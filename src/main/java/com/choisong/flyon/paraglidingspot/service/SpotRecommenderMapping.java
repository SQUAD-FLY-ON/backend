package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.dto.RecommendCriteria;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotCoordinateRepository;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotRepository;
import java.util.EnumMap;
import org.springframework.stereotype.Component;

@Component
public class SpotRecommenderMapping {

    private final EnumMap<RecommendCriteria, SpotRecommender>  recommenderMap = new EnumMap<>(RecommendCriteria.class);

    public SpotRecommenderMapping(final ParaglidingSpotCoordinateRepository coordinateRepository,
        final ParaglidingSpotRepository repository) {
        recommenderMap.put(RecommendCriteria.DISTANCE, new NearestSpotRecommender(coordinateRepository,repository));
        recommenderMap.put(RecommendCriteria.WEATHER, new GoodWeatherSpotRecommender(repository));
    }

    public SpotRecommender getRecommender(final RecommendCriteria criteria) {
        return recommenderMap.get(criteria);
    }
}
