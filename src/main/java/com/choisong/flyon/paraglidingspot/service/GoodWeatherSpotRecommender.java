package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.controller.ParaglidingSpotController;
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
    public Slice<ParaglidingSpotController> getSpotsByCriteria(final RecommendCriteria criteria, final long size) {
        return new PageImpl<>(List.of());
    }
}

