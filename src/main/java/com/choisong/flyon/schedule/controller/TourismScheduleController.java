package com.choisong.flyon.schedule.controller;

import com.choisong.flyon.schedule.service.TourismScheduleService;
import com.choisong.flyon.schedule.dto.TourismCreateRequest;
import com.choisong.flyon.security.annotation.AuthenticationMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tourism-schedule")
public class TourismScheduleController {

    private final TourismScheduleService tourismScheduleService;

    @PostMapping
    public void createTourism(@RequestBody TourismCreateRequest request, @AuthenticationMember Long memberId){
        tourismScheduleService.createTourismSchedule(request,memberId);
    }

}
