package com.choisong.flyon.schedule.dto;

import com.choisong.flyon.schedule.domain.TourismSchedule.TourismSpot;
import java.time.LocalDateTime;
import java.util.List;

public record TourismCreateRequest(
    List<List<TourismSpot>> tourismSpotList,
    LocalDateTime scheduleStart,
    LocalDateTime scheduleEnd,
    Long paraglidingSpotId
) {
}
