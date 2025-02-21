package com.personal.skin_api.common.exception.member;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum PasswordErrorCode implements ErrorCode {

    PASSWORD_CANNOT_BE_NULL(BAD_REQUEST, "비밀번호는 NULL일 수 없습니다."),
    PASSWORD_CANNOT_CONTAINS_BLANK(BAD_REQUEST, "비밀번호에 공백을 포함할 수 없습니다."),
    INVALID_PASSWORD_LENGTH(BAD_REQUEST, "비밀번호의 길이가 올바르지 않습니다."),
    INVALID_PASSWORD_FORMAT(BAD_REQUEST, "비밀번호는 최소 1개의 알파벳, 숫자, 특수문자(!, @, ? 만)를 포함해야 합니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
