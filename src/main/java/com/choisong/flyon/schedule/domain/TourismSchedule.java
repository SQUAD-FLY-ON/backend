package com.choisong.flyon.schedule.domain;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tourism_schedule")
@Getter
@NoArgsConstructor
public class TourismSchedule {

    @Id
    private String id;

    private Long memberId;
    private Long paraglidingSpotId;
    private LocalDateTime scheduleStart;
    private LocalDateTime scheduleEnd;

    private List<List<TourismSpot>> dailyTourismSpots ;

    @Builder
    public TourismSchedule(final Long memberId, final LocalDateTime scheduleStart, final LocalDateTime scheduleEnd,
        final List<List<TourismSpot>> dailyTourismSpots, final Long paraglidingSpotId) {
        this.memberId = memberId;
        this.scheduleStart = scheduleStart;
        this.scheduleEnd = scheduleEnd;
        this.dailyTourismSpots = dailyTourismSpots;
        this.paraglidingSpotId = paraglidingSpotId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TourismSpot {
        private String title;
        private String addr1;
        private String addr2;
        private String mapX;
        private String mapY;
        private String tel;
        private String firstImage;
    }
}
