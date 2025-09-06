package com.choisong.flyon.flightlog.controller;

import com.choisong.flyon.flightlog.dto.FlightLogRequest;
import com.choisong.flyon.flightlog.dto.FlightLogResponse;
import com.choisong.flyon.flightlog.service.FlightLogService;
import com.choisong.flyon.security.annotation.AuthenticationMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/flight-logs")
@RequiredArgsConstructor
@Tag(name = "FlightLog", description = "비행 기록 관련 API")
public class FlightLogController {

    private final FlightLogService flightLogService;

    @Operation(summary = "비행 기록 생성", description = "비행 기록을 저장합니다.")
    @PostMapping
    public FlightLogResponse create(@RequestBody FlightLogRequest request,
        @AuthenticationMember Long memberId) {
        return flightLogService.create(request, memberId);
    }

    @Operation(summary = "내 비행 기록 목록 조회", description = "내 비행 기록을 최신순으로 무한스크롤 방식으로 조회합니다.")
    @GetMapping("/me")
    public Slice<FlightLogResponse> getMyFlightLogs(@AuthenticationMember Long memberId,
        @RequestParam int page,
        @RequestParam int size) {
        return flightLogService.getMyFlightLogs(memberId, page, size);
    }

    @Operation(summary = "비행 기록 삭제", description = "로그인한 사용자의 비행 기록을 삭제합니다.")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id,
        @AuthenticationMember Long memberId) {
        flightLogService.delete(id, memberId);
    }
}
