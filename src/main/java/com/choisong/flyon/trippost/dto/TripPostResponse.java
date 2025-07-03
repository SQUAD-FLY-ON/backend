package com.choisong.flyon.trippost.dto;

import java.time.LocalDateTime;

public record TripPostResponse(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}