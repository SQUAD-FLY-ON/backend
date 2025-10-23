package com.choisong.flyon.flightlog.controller;

import com.choisong.flyon.flightlog.dto.FlightTrackResponse;
import com.choisong.flyon.flightlog.dto.FlightTrackUpsertRequest;
import com.choisong.flyon.flightlog.service.FlightTrackService;
import com.choisong.flyon.security.annotation.AuthenticationMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flight-logs")
@RequiredArgsConstructor
@Tag(name = "FlightTrack", description = "비행 트랙(위도/경도/고도) API")
public class FlightTrackController {

    private final FlightTrackService flightTrackService;

    @Operation(summary = "트랙 저장/갱신", description = "위도/경도/고도 리스트를 저장하거나 덮어씁니다.")
    @PutMapping("/{flightLogId}/track")
    public FlightTrackResponse upsert(@PathVariable("flightLogId") String flightLogId,
                                      @AuthenticationMember Long memberId,
                                      @RequestBody FlightTrackUpsertRequest req) {
        flightTrackService.upsert(flightLogId, memberId, req);
        return flightTrackService.get(flightLogId, memberId);
    }

    @Operation(summary = "트랙 조회(포인트만 반환)", description = "해당 비행 기록의 포인트 리스트만 반환합니다.")
    @GetMapping("/{paraglidingSpotId}/track")
    public FlightTrackResponse get(@PathVariable Long paraglidingSpotId) {
        return flightTrackService.getByParaglidingSpotId(paraglidingSpotId);
    }
}