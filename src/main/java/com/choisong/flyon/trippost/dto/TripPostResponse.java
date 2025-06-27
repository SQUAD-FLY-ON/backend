package com.choisong.flyon.trippost.dto;

import java.time.LocalDate;

public record TripPostResponse(
        Long id,
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate
) {}