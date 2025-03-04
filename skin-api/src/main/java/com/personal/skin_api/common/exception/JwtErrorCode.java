package com.personal.skin_api.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum JwtErrorCode implements ErrorCode {

    JWT_CANNOT_BE_NULL(BAD_REQUEST, "토큰이 NULL일 수 없습니다."),
    INVALID_JWT(BAD_REQUEST, "토큰을 사용할 수 없습니다."),
    EXPIRED_JWT(BAD_REQUEST, "토큰이 만료되었습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
