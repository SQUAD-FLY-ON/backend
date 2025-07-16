package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.dto.RecommendCriteria;
import java.util.EnumMap;
import org.springframework.stereotype.Component;

@Component
public class SpotRecommenderMapping {

    private final EnumMap<RecommendCriteria, SpotRecommender>  recommenderMap = new EnumMap<>(RecommendCriteria.class);

    public SpotRecommenderMapping() {
        recommenderMap.put(RecommendCriteria.DISTANCE, new NearestSpotRecommender());
        recommenderMap.put(RecommendCriteria.WEATHER, new GoodWeatherSpotRecommender());
    }

    public SpotRecommender getRecommender(final RecommendCriteria criteria) {
        return recommenderMap.get(criteria);
    }
}
