package com.choisong.flyon.trippost.dto;

import java.time.LocalDateTime;

public record TripPostCommentResponse(
    Long id,
    Long memberId,
    String content,
    LocalDateTime createdAt
) {

}
