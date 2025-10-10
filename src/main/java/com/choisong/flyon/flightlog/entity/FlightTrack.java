package com.choisong.flyon.flightlog.entity;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "flight_tracks")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightTrack {

    @Id
    private String id;

    private Long paraglidingSpotId;

    private String flightLogId;

    private Long memberId;

    private List<TrackPoint> points;

    private LocalDateTime updatedAt;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TrackPoint {

        private double latitude;
        private double longitude;
        private double altitude;
        private LocalDateTime time;
    }
}
