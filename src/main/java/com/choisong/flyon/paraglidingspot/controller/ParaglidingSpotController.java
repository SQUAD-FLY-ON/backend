package com.choisong.flyon.paraglidingspot.controller;

import com.choisong.flyon.paraglidingspot.dto.ParaglidingSpotRecommendRequest;
import com.choisong.flyon.paraglidingspot.dto.ParaglidingSpotRecommendResponse;
import com.choisong.flyon.paraglidingspot.dto.SearchBoxRequest;
import com.choisong.flyon.paraglidingspot.dto.SearchedSpotResponse;
import com.choisong.flyon.paraglidingspot.service.ParaglidingSpotService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/paragliding-spot")
@RequiredArgsConstructor
public class ParaglidingSpotController {

    private final ParaglidingSpotService paraglidingSpotService;

    @GetMapping("/recommend")
    public ParaglidingSpotRecommendResponse recommendSpots(@RequestBody ParaglidingSpotRecommendRequest request){
        return paraglidingSpotService.recommendSpot(request);
    }

    @GetMapping
    public SearchedSpotResponse searchSpots(@ModelAttribute SearchBoxRequest request){
        return paraglidingSpotService.searchSpots(request);
    }
}
