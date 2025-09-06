package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.dto.RecommendCriteria;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotCoordinateRepository;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotRepository;
import com.choisong.flyon.weather.repository.WeatherRepository;
import java.util.EnumMap;
import org.springframework.stereotype.Component;

@Component
public class SpotRecommenderMapping {

    private final EnumMap<RecommendCriteria, SpotRecommender> recommenderMap = new EnumMap<>(RecommendCriteria.class);

    public SpotRecommenderMapping(final ParaglidingSpotCoordinateRepository coordinateRepository,
        final ParaglidingSpotRepository spotRepository, final WeatherRepository weatherRepository) {
        recommenderMap.put(RecommendCriteria.DISTANCE,
            new NearestSpotRecommender(coordinateRepository, spotRepository));
        recommenderMap.put(RecommendCriteria.WEATHER,
            new GoodWeatherSpotRecommender(spotRepository, weatherRepository));
    }

    public SpotRecommender getRecommender(final RecommendCriteria criteria) {
        return recommenderMap.get(criteria);
    }
}
