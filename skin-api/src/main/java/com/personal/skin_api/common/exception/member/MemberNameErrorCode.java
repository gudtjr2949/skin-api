package com.personal.skin_api.common.exception.member;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum MemberNameErrorCode implements ErrorCode {

    NAME_CANNOT_BE_NULL(BAD_REQUEST, "이름이 NULL일 수 없습니다."),
    NAME_CANNOT_CONTAINS_BLANK(BAD_REQUEST, "이름에 공백을 포함할 수 없습니다."),
    INVALID_NAME_LENGTH(BAD_REQUEST, "이름 길이가 가능 범위를 벗어났습니다."),
    INVALID_NAME_FORMAT(BAD_REQUEST, "이름에 특수문자와 숫자는 포함될 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
