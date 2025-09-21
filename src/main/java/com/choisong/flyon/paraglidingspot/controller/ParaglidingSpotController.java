package com.choisong.flyon.paraglidingspot.controller;

import com.choisong.flyon.paraglidingspot.dto.ParaglidingSpotRecommendRequest;
import com.choisong.flyon.paraglidingspot.dto.ParaglidingSpotRecommendResponse;
import com.choisong.flyon.paraglidingspot.dto.SearchedSpotResponse;
import com.choisong.flyon.paraglidingspot.dto.SpotResponse;
import com.choisong.flyon.paraglidingspot.service.ParaglidingSpotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/paragliding-spot")
@RequiredArgsConstructor
@Tag(name = "패러글라이딩 체험장")
public class ParaglidingSpotController {

    private final ParaglidingSpotService paraglidingSpotService;

    @GetMapping("/recommend")
    @Operation(summary = "체험장 추천[기준 : 거리/날씨]", description = "위도/경도와 거리/날씨 기준을 이용해 체험장 정보를 반환합니다.")
    public ParaglidingSpotRecommendResponse recommendSpots(@ModelAttribute ParaglidingSpotRecommendRequest request) {
        return paraglidingSpotService.recommendSpot(request);
    }

    @GetMapping
    @Operation(summary = "체험장 탐색", description = "광역시와 도 단위의 문자열로 체험장을 검색합니다. 가능한 지역 (경기도,강원도,경상남도,경상북도,광주광역시,울산광역시,"
        + "대구광역시,대전광역시,부산광역시,제주특별자치도,충청남도,충청북도)")
    public SearchedSpotResponse searchSpots(@RequestParam(required = false) String sido,
        @RequestParam(required = false) String sigungu) {
        return paraglidingSpotService.searchSpots(sido,sigungu);
    }

    @GetMapping("/{spotId}")
    @Operation(summary = "체험장 상세 조회", description = "체험장의 상세 정보를 반환합니다.(웹사이트, 전화번호 등)")
    public SpotResponse findSpot(@PathVariable Long spotId) {
        return paraglidingSpotService.findSpotResponseById(spotId);
    }
}
