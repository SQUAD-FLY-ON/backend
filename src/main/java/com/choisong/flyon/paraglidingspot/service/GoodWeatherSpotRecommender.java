package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.controller.ParaglidingSpotController;
import com.choisong.flyon.paraglidingspot.domain.ParaglidingSpot;
import com.choisong.flyon.paraglidingspot.dto.RecommendCriteria;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoodWeatherSpotRecommender implements SpotRecommender {

    private final ParaglidingSpotRepository paraglidingSpotRepository;

    @Override
    public List<ParaglidingSpot> getSpotsByCurrentLocation(final double latitude, final double longitude) {
        return List.of();
    }
}

