package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.controller.ParaglidingSpot;
import com.choisong.flyon.paraglidingspot.dto.RecommendCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NearestSpotRecommender implements SpotRecommender {



    @Override
    public Slice<ParaglidingSpot> getSpotsByCriteria(final RecommendCriteria criteria, final long size) {
        return null;
    }
}
