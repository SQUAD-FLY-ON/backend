package com.choisong.flyon.schedule.service;

import com.choisong.flyon.schedule.domain.TourismSchedule;
import com.choisong.flyon.schedule.dto.TourismCreateRequest;
import com.choisong.flyon.schedule.repository.TourismScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Service
@Transactional
public class TourismScheduleService {

    private final TourismScheduleRepository tourismScheduleRepository;

    public void createTourismSchedule(@RequestBody TourismCreateRequest request, final Long memberId){
        final TourismSchedule schedule = TourismSchedule.builder()
            .dailyTourismSpots(request.tourismSpotList())
            .scheduleStart(request.scheduleStart())
            .scheduleEnd(request.scheduleEnd())
            .memberId(memberId)
            .paraglidingSpotId(request.paraglidingSpotId())
            .build();
        tourismScheduleRepository.save(schedule);
    }

}
