package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.controller.ParaglidingSpot;
import com.choisong.flyon.paraglidingspot.dto.RecommendCriteria;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoodWeatherSpotRecommender implements SpotRecommender {

    private final ParaglidingSpotRepository paraglidingSpotRepository;

    @Override
    public Slice<ParaglidingSpot> getSpotsByCriteria(final RecommendCriteria criteria, final long size) {

    }
}

