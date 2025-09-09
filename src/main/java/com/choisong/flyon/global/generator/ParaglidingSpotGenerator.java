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
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
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

            // 초기 기상 데이터 채움 (순차 실행)
            CompletableFuture.runAsync(this::runWeatherSchedulersWithRetry);
        }
    }

    private void runWeatherSchedulersWithRetry() {
        // 단기 → 중기 순으로 순차 실행
        runWithRetry(
            "WeatherScheduler.scheduleVilageWeather",
            weatherScheduler::scheduleVilageWeather,
            5,
            2000L
        );

        runWithRetry(
            "WeatherScheduler.scheduleMidWeather",
            weatherScheduler::scheduleMidWeather,
            5,
            2000L
        );
    }

    /** 주어진 작업을 개별 RetryTemplate로 재시도 실행 */
    private void runWithRetry(final String name, final Runnable action, final int maxAttempts, final long backoffMs) {
        RetryTemplate retryTemplate = new RetryTemplate();

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(maxAttempts);
        retryTemplate.setRetryPolicy(retryPolicy);

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(backoffMs);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        try {
            retryTemplate.execute(context -> {
                int attempt = context.getRetryCount() + 1;
                log.info("[{}] 실행 시도 ({}번째)", name, attempt);
                action.run();
                log.info("[{}] 실행 성공 ({}번째 시도)", name, attempt);
                return null;
            });
        } catch (Exception e) {
            log.error("[{}] 초기 실행 최종 실패 (maxAttempts={})", name, maxAttempts, e);
        }
    }

    private void saveWeather(final List<ParaglidingSpotCsv> dtos) {
        Set<String> set = new HashSet<>();
        List<Weather> weathers = new ArrayList<>();
        dtos.forEach(dto -> {
            String key = dto.getSido() + dto.getSigungu();
            if (!set.contains(key)) {
                weathers.add(new Weather(
                    dto.getSido(),
                    dto.getSigungu(),
                    dto.getMidTempCode(),
                    dto.getMidWeatherCode(),
                    dto.getX().intValue(),
                    dto.getY().intValue()
                ));
                set.add(key);
            }
        });
        weatherRepository.saveAll(weathers);
    }

    private void saveToRedis(final List<ParaglidingSpot> entities) {
        coordinateRepository.flushAllCoordinates();
        entities.forEach(e -> coordinateRepository.addLocation(
            e.getSpotCoordinate().getLatitude(),
            e.getSpotCoordinate().getLongitude(),
            e.getId()
        ));
    }

    private List<ParaglidingSpot> getParaglidingSpots(final List<ParaglidingSpotCsv> dtos) {
        return dtos.stream()
            .map(mapper::toEntity)
            .toList();
    }
}
