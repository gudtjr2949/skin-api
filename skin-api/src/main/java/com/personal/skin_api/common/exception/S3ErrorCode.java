package com.personal.skin_api.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum S3ErrorCode implements ErrorCode {

    S3_UPLOAD_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "S3 업로드 중 예외가 발생했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}