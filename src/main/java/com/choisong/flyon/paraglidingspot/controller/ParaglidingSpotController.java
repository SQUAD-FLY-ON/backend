package com.choisong.flyon.paraglidingspot.controller;

import com.choisong.flyon.paraglidingspot.dto.ParaglidingSpotRecommendRequest;
import com.choisong.flyon.paraglidingspot.dto.ParaglidingSpotRecommendResponse;
import com.choisong.flyon.paraglidingspot.dto.SearchBoxRequest;
import com.choisong.flyon.paraglidingspot.dto.SearchedSpotResponse;
import com.choisong.flyon.paraglidingspot.dto.SpotResponse;
import com.choisong.flyon.paraglidingspot.service.ParaglidingSpotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/paragliding-spot")
@RequiredArgsConstructor
@Tag(name = "패러글라이딩 체험장")
public class ParaglidingSpotController {

    private final ParaglidingSpotService paraglidingSpotService;

    @GetMapping("/recommend")
    @Operation(summary = "체험장 추천[기준 : 거리/날씨]", description = "위도/경도와 거리/날씨 기준을 이용해 체험장 정보를 반환합니다.")
    public ParaglidingSpotRecommendResponse recommendSpots(@RequestBody ParaglidingSpotRecommendRequest request) {
        return paraglidingSpotService.recommendSpot(request);
    }

    @GetMapping
    @Operation(summary = "체험장 탐색[지도]", description = "지도의 중심좌표와 모서리 좌표(방향 무관)를 이용해 체험장을 조회합니다. 체험장 상세 정보까지 함께 반환합니다.")
    public SearchedSpotResponse searchSpots(@ModelAttribute SearchBoxRequest request) {
        return paraglidingSpotService.searchSpots(request);
    }

    @GetMapping("/{spotId}")
    @Operation(summary = "체험장 상세 조회", description = "체험장의 상세 정보를 반환합니다.(웹사이트, 전화번호 등)")
    public SpotResponse findSpot(@PathVariable Long spotId) {
        return paraglidingSpotService.findById(spotId);
    }
}
