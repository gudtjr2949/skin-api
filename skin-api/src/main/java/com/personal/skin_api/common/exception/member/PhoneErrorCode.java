package com.personal.skin_api.common.exception.member;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum PhoneErrorCode implements ErrorCode {

    PHONE_CANNOT_BE_NULL(BAD_REQUEST, "전화번호는 NULL일 수 없습니다."),
    PHONE_CANNOT_CONTAINS_BLANK(BAD_REQUEST, "전화번호에 공백을 포함할 수 없습니다."),
    INVALID_PHONE_LENGTH(BAD_REQUEST, "전화번호의 길이가 올바르지 않습니다."),
    INVALID_PHONE_FORMAT(BAD_REQUEST, "전화번호는 숫자로만 표현될 수 있습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
