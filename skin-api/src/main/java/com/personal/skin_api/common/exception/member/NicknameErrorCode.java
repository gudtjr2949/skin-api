package com.personal.skin_api.common.exception.member;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum NicknameErrorCode implements ErrorCode {

    NICKNAME_CANNOT_BE_NULL(BAD_REQUEST, "닉네임이 NULL일 수 없습니다."),
    NICKNAME_CANNOT_CONTAINS_BLANK(BAD_REQUEST, "닉네임에 공백을 포함할 수 없습니다."),
    INVALID_NICKNAME_LENGTH(BAD_REQUEST, "닉네임 길이가 올바르지 않습니다."),
    INVALID_NICKNAME_FORMAT(BAD_REQUEST, "닉네임에 특수문자를 포함할 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
