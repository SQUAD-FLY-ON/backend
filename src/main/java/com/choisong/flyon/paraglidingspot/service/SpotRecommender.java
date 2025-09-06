package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.domain.ParaglidingSpot;
import java.util.List;

public interface SpotRecommender {

    List<ParaglidingSpot> getSpotsByCurrentLocation(double latitude, double longitude);
}
