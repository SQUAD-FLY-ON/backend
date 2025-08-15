package com.choisong.flyon.member.dto;

public record MemberInfoResponse(
    String nickname,
    String gliderBadge,
    Integer badgeAltitude,
    Integer totalJumpAltitude
) {

}
