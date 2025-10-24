package com.choisong.flyon.schedule.domain;

import com.choisong.flyon.schedule.dto.TourismType;
import jakarta.persistence.Id;
import java.time.LocalDate;
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

    private LocalDate scheduleStart;
    private LocalDate scheduleEnd;
    private List<List<TourismSpot>> dailyTourismSpots;
    private String tourName;

    @Builder
    public TourismSchedule(final Long memberId, final LocalDate scheduleStart, final LocalDate scheduleEnd,
        final List<List<TourismSpot>> dailyTourismSpots, final String tourName) {
        this.memberId = memberId;
        this.scheduleStart = scheduleStart;
        this.scheduleEnd = scheduleEnd;
        this.dailyTourismSpots = dailyTourismSpots;
        this.tourName = tourName;
    }

    @Override
    public String toString() {
        return "TourismSchedule{" +
            "id=" + id +
            ", name='" + tourName + '\'' +
            ", scheduleStart=" + scheduleStart +
            ", scheduleEnd=" + scheduleEnd +
            '}';
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TourismSpot {

        private Long id;
        private TourismType tourismType;
        private String name;
        private String fullAddress;
        private double longitude;
        private double latitude;
        private String phoneNumber;
        private String imgUrl;
    }
}
