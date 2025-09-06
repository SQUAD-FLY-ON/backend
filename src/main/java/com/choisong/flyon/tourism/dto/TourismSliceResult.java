package com.choisong.flyon.tourism.dto;

import java.util.List;

public record TourismSliceResult(
    List<TourismResponse> content,
    boolean hasNext,
    int totalCount
) {

}