package com.choisong.flyon.global.generator;

import com.choisong.flyon.paraglidingspot.domain.ParaglidingSpot;
import com.choisong.flyon.paraglidingspot.mapper.ParaglidingSpotMapper;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotCoordinateRepository;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParaglidingSpotGenerator implements CommandLineRunner {

    private final ParaglidingSpotRepository repository;
    private final ParaglidingSpotMapper mapper;
    private final ParaglidingSpotCoordinateRepository coordinateRepository;

    @Override
    public void run(final String... args) throws Exception {
        if (repository.count() > 0) {
            return;
        }
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("data/paragliding-spot.csv");
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            List<ParaglidingSpotCsv> dtos = getParaglidingSpotCsvs(reader);
            List<ParaglidingSpot> entities = getParaglidingSpots(dtos);
            repository.saveAll(entities);
            saveToRedis(entities);
        }
    }

    private void saveToRedis(final List<ParaglidingSpot> entities) {
        coordinateRepository.flushAllCoordinates();
        entities.forEach(e->coordinateRepository.addLocation(e.getCoordinate().getLatitude(),
            e.getCoordinate().getLongitude(),e.getId()));
    }

    private List<ParaglidingSpot> getParaglidingSpots(final List<ParaglidingSpotCsv> dtos) {
        return dtos.stream()
            .map(mapper::toEntity)
            .toList();
    }

    private static List<ParaglidingSpotCsv> getParaglidingSpotCsvs(final InputStreamReader reader) {
        return new CsvToBeanBuilder<ParaglidingSpotCsv>(reader)
            .withType(ParaglidingSpotCsv.class)
            .withIgnoreLeadingWhiteSpace(true)
            .build()
            .parse();
    }
}
