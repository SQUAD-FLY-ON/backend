package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.domain.ParaglidingSpot;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotRepository;
import com.choisong.flyon.weather.domain.Weather;
import com.choisong.flyon.weather.repository.WeatherRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoodWeatherSpotRecommender implements SpotRecommender {

    private final ParaglidingSpotRepository paraglidingSpotRepository;
    private final WeatherRepository weatherRepository;

    @Override
    public List<ParaglidingSpot> getSpotsByCurrentLocation(final double latitude, final double longitude) {
        List<Weather> weathers = weatherRepository.findAll();
        List<Weather> goodWeather = weathers.stream().filter(Weather::isGoodWeatherExist)
            .toList();
        List<ParaglidingSpot> goodWeatherSpot = new ArrayList<>();
        goodWeather.forEach(w -> goodWeatherSpot.addAll(paraglidingSpotRepository.findByAddress(w.getSido(),
            w.getSigungu())));
        return goodWeatherSpot;
    }
}

