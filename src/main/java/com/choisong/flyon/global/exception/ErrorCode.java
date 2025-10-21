package com.choisong.flyon.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    /**
     * Auth Error 0~99
     */
    AUTHORITY_NOT_VALID(HttpStatus.FORBIDDEN, "000", "접근 권한이 없습니다."),
    PROVIDER_NOT_FOUND(HttpStatus.NOT_FOUND, "001", "지원하지 않는 소셜로그인 공급자입니다."),
    CONVERTING_FAILED(HttpStatus.BAD_REQUEST, "002", "요청한 소셜로그인 공급자를 변환할 수 없습니다."),
    REFRESHTOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "003", "리프레쉬 토큰을 찾을 수 없습니다"),
    REFRESHTOKEN_COOKIE_NOT_FOUND(HttpStatus.NOT_FOUND, "004", "리프레쉬 토큰을 쿠키에서 찾을 수 없습니다."),
    ACCESSTOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "005", "액세스토큰이 만료됐습니다."),
    CAN_NOT_PARSE_TOKEN(HttpStatus.UNAUTHORIZED, "006", "복호화할 수 없는 잘못된 토큰입니다."),
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "007", "이 에러 메세지를 본다면 서버 개발자에게 꼭 연락해주세요."),
    ACCESSTOKEN_HEADER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "008", "요청 헤더에서 액세스토큰을 찾을 수 없습니다."),
    PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST, "009", "비밀번호가 일치하지 않습니다."),
    REFRESHTOKEN_EXPIRED(HttpStatus.UNAUTHORIZED,"010","리프레쉬토큰이 만료됐습니다"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST,"011","입력값 오류"),

    /**
     * Member Error 100~199
     */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "100", "회원 정보를 찾을 수 없습니다."),
    LOGIN_ID_DUPLICATED(HttpStatus.BAD_REQUEST, "101", "중복된 로그인 아이디입니다."),
    NICKNAME_DUPLICATED(HttpStatus.BAD_REQUEST, "102", "중복된 닉네임입니다."),

    /**
     * TripPost Error 200~299
     */
    TRIP_POST_NOT_FOUND(HttpStatus.NOT_FOUND, "200", "여행 게시글을 찾을 수 없습니다."),
    TRIP_POST_ACCESS_DENIED(HttpStatus.FORBIDDEN, "201", "본인의 글만 수정/삭제할 수 있습니다."),
    TRIP_POST_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "202", "좋아요 정보가 존재하지 않습니다."),
    TRIP_POST_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "203", "댓글을 찾을 수 없습니다."),
    TRIP_POST_COMMENT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "204", "댓글에 대한 권한이 없습니다."),

    /**
     * FlightLog Error 300~399
     */
    FLIGHT_LOG_NOT_FOUND(HttpStatus.NOT_FOUND, "300", "비행 기록을 찾을 수 없습니다."),
    FLIGHT_LOG_ACCESS_DENIED(HttpStatus.FORBIDDEN, "301", "해당 비행 기록에 접근할 수 없습니다."),

    /**
     * Paragliding Spot Erorr 400~499
     */
    SPOT_NOT_FOUND(HttpStatus.NOT_FOUND, "400", "체험장을 찾을 수 없습니다"),

    /**
     * External API 900~999
     */
    TOURISM_API_BAD_RESPONSE(HttpStatus.BAD_GATEWAY, "900", "관광정보 API 응답이 올바르지 않습니다."),
    TOURISM_API_ERROR(HttpStatus.BAD_GATEWAY, "901", "관광정보 API 호출 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String msg;

}
