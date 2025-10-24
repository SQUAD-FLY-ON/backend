package com.choisong.flyon.global.generator;

import com.choisong.flyon.flightlog.entity.FlightLog;
import com.choisong.flyon.flightlog.entity.FlightTrack;
import com.choisong.flyon.flightlog.repository.FlightLogRepository;
import com.choisong.flyon.flightlog.repository.FlightTrackRepository;
import com.choisong.flyon.paraglidingspot.mapper.ParaglidingSpotMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FlightTrackGenerator implements CommandLineRunner {

    private final FlightTrackRepository flightTrackRepository;
    private final FlightLogRepository flightLogRepository;
    private static class PointRaw {
        public double latitude;
        public double longitude;
        public double altitude;
        public String time;
    }

    @Override
    public void run(String... args) throws Exception {
        if (flightTrackRepository.count() > 0) {
            flightTrackRepository.deleteAll();
        }

        if(flightLogRepository.count() > 0) {
            flightLogRepository.deleteAll();
        }

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("data/flight_tracks.csv");
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {

            List<FlightTrackCsvRow> rows = new CsvToBeanBuilder<FlightTrackCsvRow>(reader)
                .withType(FlightTrackCsvRow.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
                .parse();

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            List<FlightTrack> batch = new ArrayList<>(rows.size());

            for (FlightTrackCsvRow r : rows) {
                List<PointRaw> rawList = mapper.readValue(
                    r.getPointsJson(),
                    new TypeReference<List<PointRaw>>() {}
                );

                List<FlightTrack.TrackPoint> points = new ArrayList<>(rawList.size());
                for (PointRaw p : rawList) {
                    points.add(FlightTrack.TrackPoint.builder()
                        .latitude(p.latitude)
                        .longitude(p.longitude)
                        .altitude(p.altitude)
                        .time(LocalDateTime.parse(p.time))
                        .build());
                }

                LocalDateTime updatedAt = LocalDateTime.parse(r.getUpdatedAt());

                FlightTrack entity = FlightTrack.builder()
                    .id(null)
                    .paraglidingSpotId(r.getParaglidingSpotId())
                    .flightLogId((r.getFlightLogId() == null || r.getFlightLogId().isBlank())
                        ? null : r.getFlightLogId())
                    .memberId(r.getMemberId() == null ? 1L : r.getMemberId())
                    .points(points)
                    .updatedAt(updatedAt)
                    .build();

                batch.add(entity);
            }

            flightTrackRepository.saveAll(batch);
            log.info("[FlightTrackGenerator] Inserted {} flight tracks.", batch.size());
        } catch (Exception e) {
            log.error("[FlightTrackGenerator] preload failed", e);
            throw e;
        }
    }
}
