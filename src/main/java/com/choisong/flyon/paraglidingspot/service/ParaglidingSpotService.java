package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.domain.ParaglidingSpot;
import com.choisong.flyon.paraglidingspot.dto.ParaglidingSpotRecommendRequest;
import com.choisong.flyon.paraglidingspot.dto.ParaglidingSpotRecommendResponse;
import com.choisong.flyon.paraglidingspot.dto.RecommendSpot;
import com.choisong.flyon.paraglidingspot.dto.SearchedSpotResponse;
import com.choisong.flyon.paraglidingspot.dto.SearchedSpotResponse.SearchedSpot;
import com.choisong.flyon.paraglidingspot.dto.SpotResponse;
import com.choisong.flyon.paraglidingspot.exception.SpotNotFoundException;
import com.choisong.flyon.paraglidingspot.mapper.ParaglidingSpotMapper;
import com.choisong.flyon.paraglidingspot.repository.ParaglidingSpotRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

    public SpotResponse findSpotResponseById(final Long id) {
        return paraglidingSpotRepository.findById(id)
            .map(paraglidingSpotMapper::toSpotResponse)
            .orElseThrow(SpotNotFoundException::notFound);
    }

    public ParaglidingSpot findById(final Long id) {
        return paraglidingSpotRepository.findById(id)
            .orElseThrow(SpotNotFoundException::notFound);
    }

    public SearchedSpotResponse searchSpots(final String sido, final String sigungu) {
        List<ParaglidingSpot> spots;

        if (sido != null && sigungu != null) {
            spots = paraglidingSpotRepository.findByAddress(sido, sigungu);
        } else if (sido != null) {
            spots = paraglidingSpotRepository.findByAddressSido(sido);
        } else if (sigungu != null) {
            spots = paraglidingSpotRepository.findByAddressSiGunGu(sigungu);
        } else {
            spots = paraglidingSpotRepository.findAll();
        }

        List<SearchedSpot> searchedSpots = spots.stream()
            .map(paraglidingSpotMapper::toSearchedSpotResponse)
            .toList();

        return new SearchedSpotResponse(searchedSpots);
    }

}


