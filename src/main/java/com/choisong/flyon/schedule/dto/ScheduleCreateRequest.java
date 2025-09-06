package com.choisong.flyon.schedule.dto;

import com.choisong.flyon.schedule.domain.TourismSchedule.TourismSpot;
import java.time.LocalDate;
import java.util.List;

public record ScheduleCreateRequest(
    List<List<TourismSpot>> schedules,
    LocalDate scheduleStart,
    LocalDate scheduleEnd
) {

}
