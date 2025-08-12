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
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WeatherScheduler {

    private final MidLandForecastClient midLandForecastClient;
    private final MidTemperatureClient midTemperatureClient;
    private final VilageForecastClient vilageForecastClient;
    private final WeatherRepository weatherRepository;
    @Value("${weather.api.key}")
    private String apiKey;

    private MidLandForecastResponse getMidLandForecastResponse(String code) {
        return midLandForecastClient.getMidLandForecast(apiKey, 1, 1, "JSON", code, DateUtil.getCurrentBaseDateHour());
    }

    private MidTemperatureResponse getMidTemperatureResponse(String code) {
        return midTemperatureClient.getMidTemperatureForecast(apiKey, 1, 1, "JSON", code,
            DateUtil.getCurrentBaseDateHour());
    }

    private VilageForecastResponse getVilageForecast(int x, int y) {
        return vilageForecastClient.getVilageForecast(apiKey, 1000, 1, "JSON", DateUtil.getCurrentDate(),
            "1400", x, y);
    }

    @Scheduled(cron = "0 15 6 * * *", zone = "Asia/Seoul")
    public void scheduleMidWeather() {
        List<Weather> weathers = weatherRepository.findAll();
        weathers.forEach(weather -> {
            String midWeatherCode = weather.getMidWeatherCode();
            String midTempCode = weather.getMidTempCode();

            MidLandForecastItem wi =
                getMidLandForecastResponse(midWeatherCode).response().body().items().item().get(0);
            MidTemperatureItem ti =
                getMidTemperatureResponse(midTempCode).response().body().items().item().get(0);

            String wf4 = normalizeMidPhrase(wi.wf4Pm());
            String wf5 = normalizeMidPhrase(wi.wf5Pm());
            String wf6 = normalizeMidPhrase(wi.wf6Pm());
            String wf7 = normalizeMidPhrase(wi.wf7Pm());
            String wf8 = normalizeMidPhrase(wi.wf8());
            String wf9 = normalizeMidPhrase(wi.wf9());
            String wf10 = normalizeMidPhrase(wi.wf10());

            weather.setMidWeather(wf4, wf5, wf6, wf7, wf8, wf9, wf10);

            weather.setMidTemp(
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
        if (s.contains("비"))    return "흐리고 비";
        if (s.contains("눈"))    return "흐리고 눈";

        return "흐림";
    }



    @Scheduled(cron = "0 15 2,5,8,11,14,17,20,23 * * *", zone = "Asia/Seoul")
    public void scheduleVilageWeather() {
        List<Weather> weathers = weatherRepository.findAll();
        weathers.forEach(weather -> {
            List<VilageForecastItem> items =
                getVilageForecast(weather.getX(), weather.getY()).response().body().items().item();
            processVilageForecastItems(weather, items);
        });
    }

    private void processVilageForecastItems(final Weather weather, final List<VilageForecastItem> items) {
        final String d1 = LocalDate.now().plusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE);
        final String d2 = LocalDate.now().plusDays(2).format(DateTimeFormatter.BASIC_ISO_DATE);
        final String d3 = LocalDate.now().plusDays(3).format(DateTimeFormatter.BASIC_ISO_DATE);

        Map<String, String> daySky = new HashMap<>();
        Map<String, String> dayPty = new HashMap<>();

        for (VilageForecastItem vw : items) {
            String date = vw.fcstDate();
            String cat  = vw.category();
            String val  = vw.fcstValue();

            if (d1.equals(date) && "TMX".equals(cat)) weather.setTemp1Max(val);
            if (d1.equals(date) && "TMN".equals(cat)) weather.setTemp1Min(val);
            if (d2.equals(date) && "TMX".equals(cat)) weather.setTemp2Max(val);
            if (d2.equals(date) && "TMN".equals(cat)) weather.setTemp2Min(val);
            if (d3.equals(date) && "TMX".equals(cat)) weather.setTemp3Max(val);
            if (d3.equals(date) && "TMN".equals(cat)) weather.setTemp3Min(val);

            if ("SKY".equals(cat)) {
                daySky.put(date, val);
            } else if ("PTY".equals(cat)) {
                if (!"0".equals(val)) {
                    dayPty.put(date, val);
                } else {
                    dayPty.putIfAbsent(date, "0");
                }
            }
        }

        if (daySky.containsKey(d1) || dayPty.containsKey(d1)) {
            String phrase = toSimpleWeather(daySky.get(d1), dayPty.getOrDefault(d1, "0"));
            if (phrase != null) weather.setWeather1(phrase);
        }
        if (daySky.containsKey(d2) || dayPty.containsKey(d2)) {
            String phrase = toSimpleWeather(daySky.get(d2), dayPty.getOrDefault(d2, "0"));
            if (phrase != null) weather.setWeather2(phrase);
        }
        if (daySky.containsKey(d3) || dayPty.containsKey(d3)) {
            String phrase = toSimpleWeather(daySky.get(d3), dayPty.getOrDefault(d3, "0"));
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
            case "1", "2", "4" ->
                "흐리고 비";
            case "3" ->
                "흐리고 눈";
            default -> null;
        };
    }
}
