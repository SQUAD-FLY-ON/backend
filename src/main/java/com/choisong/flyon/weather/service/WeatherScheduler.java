package com.choisong.flyon.weather.service;

import com.choisong.flyon.util.DateUtil;
import com.choisong.flyon.weather.domain.Weather;
import com.choisong.flyon.weather.dto.MidLandForecastResponse;
import com.choisong.flyon.weather.dto.MidLandForecastResponse.MidLandForecastItem;
import com.choisong.flyon.weather.dto.MidTemperatureResponse;
import com.choisong.flyon.weather.dto.MidTemperatureResponse.MidTemperatureItem;
import com.choisong.flyon.weather.dto.VilageForecastResponse;
import com.choisong.flyon.weather.dto.VilageForecastResponse.VilageForecastItem;
import com.choisong.flyon.weather.infrastructure.MidLandForecastClient;
import com.choisong.flyon.weather.infrastructure.MidTemperatureClient;
import com.choisong.flyon.weather.infrastructure.VilageForecastClient;
import com.choisong.flyon.weather.repository.WeatherRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
@EnableScheduling
public class WeatherScheduler {

    private final MidLandForecastClient midLandForecastClient;
    private final MidTemperatureClient midTemperatureClient;
    private final VilageForecastClient vilageForecastClient;
    private final WeatherRepository weatherRepository;

    @Value("${weather.api.key}")
    private String apiKey;

    /** 중기 문구 정규화 */
    private static String normalizeMidPhrase(String src) {
        if (src == null) return null;
        String s = src.replaceAll("\\s+", "")
            .replace("(", "")
            .replace(")", "")
            .replace(",", "");
        if ("맑음".equals(s)) return "맑음";
        if ("구름많음".equals(s)) return "구름많음";
        if ("흐림".equals(s)) return "흐림";
        if (s.contains("비/눈")) return "흐리고 비";
        if (s.contains("소나기")) return "흐리고 비";
        if (s.contains("비")) return "흐리고 비";
        if (s.contains("눈")) return "흐리고 눈";
        return "흐림";
    }

    /** ★ 06:00 전용 tmFc 계산. 06:10 이전이면 '어제 0600' */
    private String getLatestMid0600TmFc() {
        final int BUFFER_MINUTES = 10;
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        LocalTime cutoff = LocalTime.of(6, BUFFER_MINUTES);
        LocalDate baseDate = now.isAfter(cutoff) ? today : today.minusDays(1);
        String tmFc = baseDate.format(DateTimeFormatter.BASIC_ISO_DATE) + "0600";
        log.debug("Using mid tmFc = {}", tmFc);
        return tmFc;
    }

    /** 단기(동네예보) baseTime 계산 */
    private String getLatestBaseTime() {
        int[] baseHours = {2, 5, 8, 11, 14, 17, 20, 23};
        LocalTime now = LocalTime.now();
        String latest = "0200";
        for (int h : baseHours) {
            if (now.getHour() > h || (now.getHour() == h && now.getMinute() >= 10)) {
                latest = String.format("%02d00", h);
            }
        }
        return latest;
    }

    // ===== API 호출부 (재시도) =====
    @Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 2000))
    MidLandForecastResponse getMidLandForecastResponse(String code) {
        String tmFc = getLatestMid0600TmFc();
        return midLandForecastClient.getMidLandForecast(apiKey, 1, 1, "JSON", code, tmFc);
    }

    @Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 2000))
    MidTemperatureResponse getMidTemperatureResponse(String code) {
        String tmFc = getLatestMid0600TmFc();
        return midTemperatureClient.getMidTemperatureForecast(apiKey, 1, 1, "JSON", code, tmFc);
    }

    @Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 2000))
    VilageForecastResponse getVilageForecast(int x, int y) {
        String baseTime = getLatestBaseTime();
        return vilageForecastClient.getVilageForecast(
            apiKey, 1000, 1, "JSON", DateUtil.getCurrentDate(), baseTime, x, y
        );
    }

    // ===== Recover =====
    @Recover
    MidLandForecastResponse recoverMidLand(Exception e, String code) {
        log.error("MidLandForecast 최종 실패 (code={})", code, e);
        return null;
    }

    @Recover
    MidTemperatureResponse recoverMidTemp(Exception e, String code) {
        log.error("MidTemperature 최종 실패 (code={})", code, e);
        return null;
    }

    @Recover
    VilageForecastResponse recoverVilage(Exception e, int x, int y) {
        log.error("VilageForecast 최종 실패 (x={}, y={})", x, y, e);
        return null;
    }

    // ===== 스케줄 =====
    // 중기: 오전 06:15에만 실행(운영 스케줄). 앱 어디서 호출되더라도 06:00 전용 tmFc 사용하므로 안전.
    @Scheduled(cron = "0 15 6 * * *", zone = "Asia/Seoul")
    public void scheduleMidWeather() {
        log.info("중기 기상 요청 호출");
        List<Weather> weathers = weatherRepository.findAll();
        weathers.forEach(weather -> {
            MidLandForecastResponse midLandRes = getMidLandForecastResponse(weather.getMidWeatherCode());
            MidTemperatureResponse midTempRes = getMidTemperatureResponse(weather.getMidTempCode());
            if (midLandRes == null || midLandRes.response() == null || midLandRes.response().body() == null) {
                log.warn("MidLandForecast 응답 없음 (code={})", weather.getMidWeatherCode());
                return;
            }
            if (midTempRes == null || midTempRes.response() == null || midTempRes.response().body() == null) {
                log.warn("MidTemperature 응답 없음 (code={})", weather.getMidTempCode());
                return;
            }
            MidLandForecastItem wi = midLandRes.response().body().items().item().get(0);
            MidTemperatureItem ti = midTempRes.response().body().items().item().get(0);

            // ★ null-무시 세터 사용 → 없는 값은 기존값 보존
            weather.setMidWeatherNullable(
                normalizeMidPhrase(wi.wf4Pm()), normalizeMidPhrase(wi.wf5Pm()),
                normalizeMidPhrase(wi.wf6Pm()), normalizeMidPhrase(wi.wf7Pm()),
                normalizeMidPhrase(wi.wf8()),  normalizeMidPhrase(wi.wf9()),
                normalizeMidPhrase(wi.wf10())
            );

            weather.setMidTempNullable(
                ti.taMin4(), ti.taMax4(),
                ti.taMin5(), ti.taMax5(),
                ti.taMin6(), ti.taMax6(),
                ti.taMin7(), ti.taMax7(),
                ti.taMin8(), ti.taMax8(),
                ti.taMin9(), ti.taMax9(),
                ti.taMin10(), ti.taMax10()
            );
        });
    }

    // 단기: 3시간 주기(관측/발표 시간대)로 실행
    @Scheduled(cron = "0 15 2,5,8,11,14,17,20,23 * * *", zone = "Asia/Seoul")
    public void scheduleVilageWeather() {
        log.info("단기 기상 호출 요청");
        List<Weather> weathers = weatherRepository.findAll();
        weathers.forEach(weather -> {
            VilageForecastResponse res = getVilageForecast(weather.getX(), weather.getY());
            if (res == null || res.response() == null || res.response().body() == null) {
                log.warn("VilageForecast 응답 없음 (x={}, y={})", weather.getX(), weather.getY());
                return;
            }
            List<VilageForecastItem> items = res.response().body().items().item();
            processVilageForecastItems(weather, items);
        });
    }

    private void processVilageForecastItems(final Weather weather, final List<VilageForecastItem> items) {
        final String d0 = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        final String d1 = LocalDate.now().plusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE);
        final String d2 = LocalDate.now().plusDays(2).format(DateTimeFormatter.BASIC_ISO_DATE);

        Map<String, String> daySky = new HashMap<>();
        Map<String, String> dayPty = new HashMap<>();

        int todayMax = Integer.MIN_VALUE;
        int todayMin = Integer.MAX_VALUE;

        for (VilageForecastItem vw : items) {
            String date = vw.fcstDate();
            String cat = vw.category();
            String val = vw.fcstValue();

            if (d0.equals(date) && "TMP".equals(cat)) {
                int tmp = Integer.parseInt(val);
                todayMax = Math.max(todayMax, tmp);
                todayMin = Math.min(todayMin, tmp);
            }

            if (d1.equals(date) && "TMX".equals(cat)) weather.setTemp2Max(val);
            if (d1.equals(date) && "TMN".equals(cat)) weather.setTemp2Min(val);
            if (d2.equals(date) && "TMX".equals(cat)) weather.setTemp3Max(val);
            if (d2.equals(date) && "TMN".equals(cat)) weather.setTemp3Min(val);

            if ("SKY".equals(cat)) daySky.put(date, val);
            else if ("PTY".equals(cat)) dayPty.put(date, !"0".equals(val) ? val : dayPty.getOrDefault(date, "0"));
        }

        if (todayMax != Integer.MIN_VALUE) weather.setTemp1Max(String.valueOf(todayMax));
        if (todayMin != Integer.MAX_VALUE) weather.setTemp1Min(String.valueOf(todayMin));

        if (daySky.containsKey(d0) || dayPty.containsKey(d0)) {
            String phrase = toSimpleWeather(daySky.get(d0), dayPty.getOrDefault(d0, "0"));
            if (phrase != null) weather.setWeather1(phrase);
        }
        if (daySky.containsKey(d1) || dayPty.containsKey(d1)) {
            String phrase = toSimpleWeather(daySky.get(d1), dayPty.getOrDefault(d1, "0"));
            if (phrase != null) weather.setWeather2(phrase);
        }
        if (daySky.containsKey(d2) || dayPty.containsKey(d2)) {
            String phrase = toSimpleWeather(daySky.get(d2), dayPty.getOrDefault(d2, "0"));
            if (phrase != null) weather.setWeather3(phrase);
        }
    }

    private String toSimpleWeather(String skyCode, String ptyCode) {
        if (ptyCode == null) ptyCode = "0";
        if ("0".equals(ptyCode)) {
            if (skyCode == null) return null;
            return switch (skyCode) {
                case "1" -> "맑음";
                case "3" -> "구름많음";
                case "4" -> "흐림";
                default -> null;
            };
        }
        return switch (ptyCode) {
            case "1", "2", "4" -> "흐리고 비";
            case "3" -> "흐리고 눈";
            default -> null;
        };
    }
}
