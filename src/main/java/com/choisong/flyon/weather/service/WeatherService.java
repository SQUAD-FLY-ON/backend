package com.choisong.flyon.weather.service;

import com.choisong.flyon.util.DateUtil;
import com.choisong.flyon.weather.domain.Weather;
import com.choisong.flyon.weather.dto.SidoWeahterResponse;
import com.choisong.flyon.weather.dto.SidoWeahterResponse.DailyWeather;
import com.choisong.flyon.weather.dto.SidoWeahterResponse.SigunguWeather;
import com.choisong.flyon.weather.repository.WeatherRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeatherService {

    private static final long MAX_FORECAST_DAYS = 10;
    private static final ZoneId SEOUL = ZoneId.of("Asia/Seoul");
    private static final DateTimeFormatter MMDD_FMT = DateTimeFormatter.ofPattern("MMdd");
    private final WeatherRepository weatherRepository;

    public SidoWeahterResponse getSidoWeatherResponse(
        String sido,
        String tripStart,
        String tripEnd
    ) {
        long startOffset = DateUtil.daysFromToday(tripStart);
        long endOffset = DateUtil.daysFromToday(tripEnd);

        if (startOffset >= MAX_FORECAST_DAYS || endOffset <= 0) {
            return new SidoWeahterResponse(List.of());
        }

        int fromIdx = (int) Math.max(1, startOffset + 1);
        int toIdx = (int) Math.min(MAX_FORECAST_DAYS, endOffset + 1);
        LocalDate today = LocalDate.now(SEOUL);

        List<SigunguWeather> weatherInfos = weatherRepository.findAllBySido(sido).stream()
            .map(w -> new SigunguWeather(
                w.getSigungu(),
                buildDailyList(w, fromIdx, toIdx, today)
            ))
            .toList();

        return new SidoWeahterResponse(weatherInfos);
    }

    private List<DailyWeather> buildDailyList(
        Weather w,
        int fromIdx,
        int toIdx,
        LocalDate today
    ) {
        List<String> skyList = List.of(
            w.getWeather1(), w.getWeather2(), w.getWeather3(),
            w.getWeather4(), w.getWeather5(), w.getWeather6(),
            w.getWeather7(), w.getWeather8(), w.getWeather9(),
            w.getWeather10()
        );
        List<String> tempMaxList = List.of(
            w.getTemp1Max(), w.getTemp2Max(), w.getTemp3Max(),
            w.getTemp4Max(), w.getTemp5Max(), w.getTemp6Max(),
            w.getTemp7Max(), w.getTemp8Max(), w.getTemp9Max(),
            w.getTemp10Max()
        );
        List<String> tempMinList = List.of(
            w.getTemp1Min(), w.getTemp2Min(), w.getTemp3Min(),
            w.getTemp4Min(), w.getTemp5Min(), w.getTemp6Min(),
            w.getTemp7Min(), w.getTemp8Min(), w.getTemp9Min(),
            w.getTemp10Min()
        );

        return IntStream.rangeClosed(fromIdx, toIdx)
            .mapToObj(i -> new DailyWeather(
                today.plusDays(i - 1).format(MMDD_FMT),
                tempMaxList.get(i - 1),
                tempMinList.get(i - 1),
                skyList.get(i - 1)
            ))
            .toList();
    }
}
