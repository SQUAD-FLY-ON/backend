package com.choisong.flyon.tourism.dto;

import com.choisong.flyon.schedule.dto.TourismType;
import lombok.Builder;

@Builder
public record TourismResponse(
//    Long id,
    TourismType tourismType,
    String name,
    String fullAddress,
    String longitude,
    String latitude,
    String phoneNumber,
    String imgUrl
) {

}