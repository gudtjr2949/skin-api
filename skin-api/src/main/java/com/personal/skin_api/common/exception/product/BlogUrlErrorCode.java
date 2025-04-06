package com.personal.skin_api.common.exception.product;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BlogUrlErrorCode implements ErrorCode {

    BLOG_URL_CAN_NOT_BE_NULL(HttpStatus.BAD_REQUEST, "블로그 주소는 null일 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
