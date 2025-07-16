package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.dto.RecommendCriteria;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ParaglidingSpotService {

    private final ParaglidingSpotRepository paraglidingSpotRepository;
    private final SpotRecommenderMapping spotRecommenderMapping;

    public void recommandSpot(final RecommendCriteria criteria, final long size){
        final SpotRecommender recommender = spotRecommenderMapping.getRecommender(criteria);
        recommender.getSpotsByCriteria(criteria);
    }

}

