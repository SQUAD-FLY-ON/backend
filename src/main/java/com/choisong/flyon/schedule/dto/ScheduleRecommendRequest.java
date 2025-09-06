package com.choisong.flyon.schedule.dto;

import com.choisong.flyon.schedule.domain.TourismSchedule.TourismSpot;
import java.time.LocalDate;
import java.util.List;

public record ScheduleRecommendRequest(
    List<TourismSpot> tourismSpotList,
    Long paraglidingSpotId,
    LocalDate scheduleStart,
    LocalDate scheduleEnd
) {

}
