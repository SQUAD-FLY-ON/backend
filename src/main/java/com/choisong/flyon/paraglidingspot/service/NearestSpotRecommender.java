package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.domain.ParaglidingSpot;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotCoordinateRepository;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NearestSpotRecommender implements SpotRecommender {

    private final ParaglidingSpotCoordinateRepository coordinateRepository;
    private final ParaglidingSpotRepository repository;

    @Override
    public List<ParaglidingSpot> getSpotsByCurrentLocation(final double latitude, final double longitude) {
        final List<Long> paraglidingSpotIds = coordinateRepository.findNearestSpotsWithRadiusLimit(latitude,
            longitude, 150, 10);
        return repository.findAllById(paraglidingSpotIds);
    }
}
