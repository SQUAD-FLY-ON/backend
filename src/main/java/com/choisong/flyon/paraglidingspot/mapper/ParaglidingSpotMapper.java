package com.choisong.flyon.paraglidingspot.mapper;

import com.choisong.flyon.global.generator.ParaglidingSpotCsv;
import com.choisong.flyon.paraglidingspot.domain.ParaglidingSpot;
import com.choisong.flyon.paraglidingspot.dto.RecommendSpot;
import com.choisong.flyon.paraglidingspot.dto.SearchedSpotResponse;
import com.choisong.flyon.paraglidingspot.dto.SpotResponse;
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
    @Mapping(target = "imgUrl", source = "imgUrl")
    ParaglidingSpot toEntity(ParaglidingSpotCsv dto);

    @Mapping(target = "spotName", source = "facilityName")
    @Mapping(target = "imgUrl", source = "imgUrl")
    @Mapping(target = "fullAddress", source = "address.fullAddress")
    @Mapping(target = "sido",source = "address.sido")
    @Mapping(target = "latitude", source = "spotCoordinate.latitude")
    @Mapping(target = "longitude", source = "spotCoordinate.longitude")
    RecommendSpot toRecommendSpotResponse(ParaglidingSpot paraglidingSpot);

    @Mapping(target = "id", expression = "java(String.valueOf(paraglidingSpot.getId()))")
    @Mapping(target = "name", source = "facilityName")
    @Mapping(target = "fullAddress", source = "address.fullAddress")
    @Mapping(target = "imgUrl", source = "imgUrl")
    @Mapping(target = "latitude", source = "spotCoordinate.latitude")
    @Mapping(target = "longitude", source = "spotCoordinate.longitude")
    SearchedSpotResponse.SearchedSpot toSearchedSpotResponse(ParaglidingSpot paraglidingSpot);

    @Mapping(target = "name", source = "facilityName")
    @Mapping(target = "fullAddress", source = "address.fullAddress")
    SpotResponse toSpotResponse(ParaglidingSpot paraglidingSpot);
}
