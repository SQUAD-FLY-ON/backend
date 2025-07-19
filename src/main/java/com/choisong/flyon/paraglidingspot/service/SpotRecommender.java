package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.controller.ParaglidingSpotController;
import com.choisong.flyon.paraglidingspot.dto.RecommendCriteria;
import org.springframework.data.domain.Slice;

public interface SpotRecommender {

    Slice<ParaglidingSpotController> getSpotsByCriteria(RecommendCriteria criteria, long size);
}
