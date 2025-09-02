package com.choisong.flyon.tourism.controller;

import com.choisong.flyon.tourism.dto.TourismSliceResult;
import com.choisong.flyon.tourism.service.TourismService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tourism")
@RequiredArgsConstructor
@Tag(name = "Tourism", description = "한국관광공사 위치 기반 관광지 조회 API")
public class TourismController {

    private final TourismService tourismService;

    @Operation(summary = "위경도 기반 관광지 Slice 조회",
            description = "lat(위도), lon(경도), radius(미터), page(0-base), size 를 사용해 무한스크롤용 Slice 응답을 반환합니다.")
    @GetMapping("/nearby")
    public TourismSliceResult nearby(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "20000") int radius,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return tourismService.findNearbySlice(lat, lon, radius, page, size);
    }
}
