package com.choisong.flyon.schedule.controller;

import com.choisong.flyon.schedule.dto.ScheduleCreateRequest;
import com.choisong.flyon.schedule.dto.ScheduleListResponse;
import com.choisong.flyon.schedule.dto.ScheduleRecommendRequest;
import com.choisong.flyon.schedule.dto.ScheduleRecommendResponse;
import com.choisong.flyon.schedule.service.TourismScheduleService;
import com.choisong.flyon.security.annotation.AuthenticationMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tourism-schedule")
@Tag(name = "여행 일정")
public class TourismScheduleController {

    private final TourismScheduleService tourismScheduleService;

    @PostMapping("/gpt")
    @Operation(summary = "GPT 여행 계획 수립", description = "GPT 4-o 를 사용해 수립한 여행 계획을 반환합니다.")
    public ScheduleRecommendResponse getTourismScheduleByGpt(@RequestBody ScheduleRecommendRequest request) {
        return tourismScheduleService.createGptSchedule(request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "여행 계획 최종 저장", description = "GPT 기반의 일정을 사용자가 수정하고 최종적으로 서버에 저장합니다.")
    public void createTourismSchedule(@RequestBody ScheduleCreateRequest request, @AuthenticationMember Long memberId) {
        tourismScheduleService.createSchedule(request, memberId);
    }

    @GetMapping
    @Operation(summary = "여행 계획 리스트 조회", description = "회원의 최근 여행부터 계획을 리스트로 반환합니다.")
    public ScheduleListResponse getTourismSchedule(@AuthenticationMember Long memberId) {
        return tourismScheduleService.getSchedule(memberId);
    }
}
