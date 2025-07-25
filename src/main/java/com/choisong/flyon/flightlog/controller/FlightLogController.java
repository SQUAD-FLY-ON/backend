package com.choisong.flyon.flightlog.controller;

import com.choisong.flyon.flightlog.dto.FlightLogRequest;
import com.choisong.flyon.flightlog.dto.FlightLogResponse;
import com.choisong.flyon.flightlog.service.FlightLogService;
import com.choisong.flyon.security.annotation.AuthenticationMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "내 비행기록 조회 (페이징)", description = "로그인한 사용자의 비행기록을 페이지로 조회합니다.")
    @GetMapping("/me")
    public Page<FlightLogResponse> getMyLogs(
            @AuthenticationMember Long memberId,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return flightLogService.getByMemberId(memberId, pageable);
    }

    @Operation(summary = "비행 기록 삭제", description = "로그인한 사용자의 비행 기록을 삭제합니다.")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id,
                       @AuthenticationMember Long memberId) {
        flightLogService.delete(id, memberId);
    }
}
