package com.choisong.flyon.global.generator;

import com.choisong.flyon.paraglidingspot.domain.ParaglidingSpot;
import com.choisong.flyon.paraglidingspot.mapper.ParaglidingSpotMapper;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotCoordinateRepository;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotRepository;
import com.choisong.flyon.weather.domain.Weather;
import com.choisong.flyon.weather.repository.WeatherRepository;
import com.choisong.flyon.weather.service.WeatherScheduler;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParaglidingSpotGenerator implements CommandLineRunner {

    private final ParaglidingSpotRepository repository;
    private final ParaglidingSpotMapper mapper;
    private final ParaglidingSpotCoordinateRepository coordinateRepository;
    private final WeatherRepository weatherRepository;
    private final WeatherScheduler weatherScheduler;

    private static List<ParaglidingSpotCsv> getParaglidingSpotCsvs(final InputStreamReader reader) {
        return new CsvToBeanBuilder<ParaglidingSpotCsv>(reader)
            .withType(ParaglidingSpotCsv.class)
            .withIgnoreLeadingWhiteSpace(true)
            .build()
            .parse();
    }

    @Override
    public void run(final String... args) throws Exception {
        if (repository.count() > 0) {
            return;
        }
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("data/facility.csv");
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            List<ParaglidingSpotCsv> dtos = getParaglidingSpotCsvs(reader);
            List<ParaglidingSpot> entities = getParaglidingSpots(dtos);
            repository.saveAll(entities);
            saveToRedis(entities);
            saveWeather(dtos);
            weatherScheduler.scheduleMidWeather();
            weatherScheduler.scheduleVilageWeather();
        }
    }

    private void saveWeather(final List<ParaglidingSpotCsv> dtos) {
        Set<String> set = new HashSet<>();
        List<Weather> weathers = new ArrayList<>();
        dtos.forEach(dto -> {
            String key = dto.getSido() + dto.getSigungu();
            if (!set.contains(key)) {
                weathers.add(new Weather(dto.getSido(), dto.getSigungu(), dto.getMidTempCode(), dto.getMidWeatherCode()
                    , dto.getX().intValue(), dto.getY().intValue()));
                set.add(key);
            }
        });
        weatherRepository.saveAll(weathers);
    }

    private void saveToRedis(final List<ParaglidingSpot> entities) {
        coordinateRepository.flushAllCoordinates();
        entities.forEach(e -> coordinateRepository.addLocation(e.getSpotCoordinate().getLatitude(),
            e.getSpotCoordinate().getLongitude(), e.getId()));
    }

    private List<ParaglidingSpot> getParaglidingSpots(final List<ParaglidingSpotCsv> dtos) {
        return dtos.stream()
            .map(mapper::toEntity)
            .toList();
    }
}
