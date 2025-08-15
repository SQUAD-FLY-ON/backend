package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.domain.ParaglidingSpot;
import com.choisong.flyon.paraglidingspot.dto.ParaglidingSpotRecommendRequest;
import com.choisong.flyon.paraglidingspot.dto.ParaglidingSpotRecommendResponse;
import com.choisong.flyon.paraglidingspot.dto.RecommendSpot;
import com.choisong.flyon.paraglidingspot.dto.SearchBoxRequest;
import com.choisong.flyon.paraglidingspot.dto.SearchedSpotResponse;
import com.choisong.flyon.paraglidingspot.mapper.ParaglidingSpotMapper;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<RecommendSpot> list = paraglidingSpots.stream().map(paraglidingSpotMapper::toRecommendSpotResponse)
            .toList();
        return new ParaglidingSpotRecommendResponse(list);
    }

    public SearchedSpotResponse searchSpots(final SearchBoxRequest request) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point center = geometryFactory.createPoint(new Coordinate(request.centerLongitude(), request.centerLatitude()));
        int radius = DistanceCalculator.haversineDistanceMeters(request);
        return new SearchedSpotResponse(paraglidingSpotRepository.findByCenterAndRadius(center, radius)
            .stream().map(paraglidingSpotMapper::toSearchedSpotResponse).toList());
    }
}


