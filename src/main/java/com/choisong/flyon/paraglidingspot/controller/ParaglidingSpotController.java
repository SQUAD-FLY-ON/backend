package com.choisong.flyon.paraglidingspot.controller;

import com.choisong.flyon.paraglidingspot.dto.RecommendCriteria;
import com.choisong.flyon.paraglidingspot.service.ParaglidingSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/paragliding-spot")
@RequiredArgsConstructor
public class ParaglidingSpotController {

    private final ParaglidingSpotService paraglidingSpotService;

    @GetMapping("/recommend")
    public void recommendSpot(@RequestParam final RecommendCriteria criteria, @RequestParam final long size){
        paraglidingSpotService.recommendSpot(criteria,size);
    }

}
