package com.choisong.flyon.paraglidingspot.mapper;

import com.choisong.flyon.global.generator.ParaglidingSpotCsv;
import com.choisong.flyon.paraglidingspot.domain.ParaglidingSpot;
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
    @Mapping(target = "imgUrl", constant = "")  // 기본값 처리 예시
    ParaglidingSpot toEntity(ParaglidingSpotCsv dto);
}
