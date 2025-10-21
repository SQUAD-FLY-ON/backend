package com.choisong.flyon.member.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberUpdateRequest(
    @NotBlank(message = "닉네임은 필수 입력입니다")
    String nickname,
    @NotBlank(message = "로그인아이디는 필수 입력입니다.")
    String loginId,
    @NotBlank(message = "패스워드는 필수 입력입니다.")
    String password
) {

}
