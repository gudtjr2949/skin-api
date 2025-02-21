package com.personal.skin_api.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {

    INACTIVE_USER(FORBIDDEN, "비활성화된 사용자입니다."),
    INVALID_PASSWORD_LENGTH(BAD_REQUEST, "패스워드의 길이가 올바르지 않습니다."),
    INVALID_PASSWORD_COMPLEXITY(BAD_REQUEST, "패스워드는 최소 1개의 알파벳, 숫자, 특수문자(!, @, ? 만)를 포함해야 합니다.."),
    NAME_CANNOT_BE_NULL(BAD_REQUEST, "이름이 NULL일 수 없습니다."),
    INVALID_NAME_LENGTH(BAD_REQUEST, "이름 길이가 올바르지 않습니다."),
    INVALID_NAME_TYPE(BAD_REQUEST, "이름에 특수문자와 숫자는 포함될 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
