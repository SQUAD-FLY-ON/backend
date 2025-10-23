package com.choisong.flyon.member.dto;

public record MemberInfoResponse(
    String loginId,
    String nickname,
    String gliderBadge,
    Integer badgeAltitude,
    Integer totalJumpAltitude
) {

}
