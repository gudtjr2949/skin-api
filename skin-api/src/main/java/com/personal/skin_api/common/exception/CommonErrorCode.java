package com.personal.skin_api.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "입력된 파라미터 값이 올바르지 않습니다."),
    PARAMETER_CAN_NOT_BE_NULL(HttpStatus.BAD_REQUEST, "입력된 파라미터가 null일 수 없습니다."),
    INVALID_HTTP_METHOD(HttpStatus.BAD_REQUEST, "잘못된 HTTP Method 요청입니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에서 에러가 발생했습니다."),
    JSON_TO_STRING_ERROR(HttpStatus.BAD_REQUEST, "JSON에서 String 변환 과정에서 에러가 발생했습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
