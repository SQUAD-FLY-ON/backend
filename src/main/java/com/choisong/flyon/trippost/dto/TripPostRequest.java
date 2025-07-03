package com.choisong.flyon.trippost.dto;

import java.time.LocalDate;

public record TripPostRequest(
        String title,
        String content
) {}
