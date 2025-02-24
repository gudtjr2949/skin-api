package com.personal.skin_api.common.exception.member;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum EmailErrorCode implements ErrorCode {

    EMAIL_CANNOT_BE_NULL(BAD_REQUEST, "이메일이 NULL일 수 없습니다."),
    EMAIL_CANNOT_CONTAINS_BLANK(BAD_REQUEST, "이메일에 공백을 포함할 수 없습니다."),
    INVALID_EMAIL_FORMAT(BAD_REQUEST, "올바르지 않은 이메일 포맷입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
