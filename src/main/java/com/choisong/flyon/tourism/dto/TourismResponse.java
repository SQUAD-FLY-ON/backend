package com.choisong.flyon.tourism.dto;

import lombok.Builder;

@Builder
public record TourismResponse(
        String title,
        String addr1,
        String addr2,
        String mapX, // 경도
        String mapY, // 위도
        String tel,
        String firstImage
) {}