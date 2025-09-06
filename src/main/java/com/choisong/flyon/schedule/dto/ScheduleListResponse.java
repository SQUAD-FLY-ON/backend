package com.choisong.flyon.schedule.dto;

import com.choisong.flyon.schedule.domain.TourismSchedule;
import java.util.List;

public record ScheduleListResponse(List<TourismSchedule> tourismSchedules) {

}
