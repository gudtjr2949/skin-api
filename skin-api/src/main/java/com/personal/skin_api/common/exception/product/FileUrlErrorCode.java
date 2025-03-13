package com.personal.skin_api.common.exception.product;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum FileUrlErrorCode implements ErrorCode {

    FILE_URL_CANNOT_BE_NULL(BAD_REQUEST, "파일경로가 NULL일 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
