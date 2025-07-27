package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.domain.ParaglidingSpot;
import com.choisong.flyon.paraglidingspot.dto.ParaglidingSpotRecommendRequest;
import com.choisong.flyon.paraglidingspot.dto.ParaglidingSpotRecommendResponse;
import com.choisong.flyon.paraglidingspot.dto.RecommendSpot;
import com.choisong.flyon.paraglidingspot.mapper.ParaglidingSpotMapper;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ParaglidingSpotService {

    private final ParaglidingSpotRepository paraglidingSpotRepository;
    private final ParaglidingSpotMapper paraglidingSpotMapper;
    private final SpotRecommenderMapping spotRecommenderMapping;

    public ParaglidingSpotRecommendResponse recommendSpot(final ParaglidingSpotRecommendRequest request) {
        final SpotRecommender recommender = spotRecommenderMapping.getRecommender(request.criteria());
        List<ParaglidingSpot> paraglidingSpots = recommender.getSpotsByCurrentLocation(request.latitude(),
            request.longitude());
        List<RecommendSpot> list = paraglidingSpots.stream().map(paraglidingSpotMapper::toResponse)
            .toList();
        return new ParaglidingSpotRecommendResponse(list);
    }
}


