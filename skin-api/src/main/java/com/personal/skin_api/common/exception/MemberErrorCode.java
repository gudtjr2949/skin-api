package com.personal.skin_api.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {

    INACTIVE_USER(FORBIDDEN, "비활성화된 사용자입니다."),
    INVALID_PASSWORD_LENGTH(BAD_REQUEST, "패스워드의 길이가 올바르지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
