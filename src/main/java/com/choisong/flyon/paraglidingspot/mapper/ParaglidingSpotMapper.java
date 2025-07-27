package com.choisong.flyon.paraglidingspot.mapper;

import com.choisong.flyon.global.generator.ParaglidingSpotCsv;
import com.choisong.flyon.paraglidingspot.domain.ParaglidingSpot;
import com.choisong.flyon.paraglidingspot.dto.ParaglidingSpotRecommendResponse;
import com.choisong.flyon.paraglidingspot.dto.RecommendSpot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParaglidingSpotMapper {

    @Mapping(target = "sido", source = "sido")
    @Mapping(target = "sigungu", source = "sigungu")
    @Mapping(target = "eupmyeondong", source = "eupmyondong")
    @Mapping(target = "fullAddress", source = "fullAddress")
    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    @Mapping(target = "imgUrl", constant = "")
    ParaglidingSpot toEntity(ParaglidingSpotCsv dto);

    @Mapping(target = "paraglidingSpotId", source = "id")
    @Mapping(target = "spotName", source = "facilityName")
    @Mapping(target = "imgUrl", source = "imgUrl")
    @Mapping(target = "starts", constant = "0.0")
    RecommendSpot toResponse(ParaglidingSpot paraglidingSpot);
}
