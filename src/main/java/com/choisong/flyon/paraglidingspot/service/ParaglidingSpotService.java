package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.dto.ParaglidingSpotRecommendRequest;
import com.choisong.flyon.paraglidingspot.dto.RecommendCriteria;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ParaglidingSpotService {

    private final ParaglidingSpotRepository paraglidingSpotRepository;
    private final SpotRecommenderMapping spotRecommenderMapping;

    public void recommendSpot(@RequestBody ParaglidingSpotRecommendRequest request){
        final SpotRecommender recommender = spotRecommenderMapping.getRecommender(request.criteria());
        recommender.getSpotsByCurrentLocation(request.latitude(), request.longitude());
    }

}


