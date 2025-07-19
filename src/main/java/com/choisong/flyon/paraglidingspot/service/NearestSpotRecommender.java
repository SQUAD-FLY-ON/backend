package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.controller.ParaglidingSpotController;
import com.choisong.flyon.paraglidingspot.dto.RecommendCriteria;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotCoordinateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NearestSpotRecommender implements SpotRecommender {

    private final ParaglidingSpotCoordinateRepository coordinateRepository;

    @Override
    public Slice<ParaglidingSpotController> getSpotsByCriteria(final RecommendCriteria criteria, final long size) {
        coordinateRepository.findNearestSpotsWithRadiusLimit()
    }
}
