package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.controller.ParaglidingSpot;
import com.choisong.flyon.paraglidingspot.dto.RecommendCriteria;
import org.springframework.data.domain.Slice;

public interface SpotRecommender {

    Slice<ParaglidingSpot> getSpotsByCriteria(RecommendCriteria criteria, long size);
}
