package com.choisong.flyon.schedule.dto;

import com.choisong.flyon.schedule.domain.TourismSchedule.TourismSpot;
import java.util.List;

public record ScheduleRecommendResponse(
    List<List<TourismSpot>> schedules

) {

}
