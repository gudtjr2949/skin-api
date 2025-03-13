package com.personal.skin_api.common.exception.product;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductContentErrorCode implements ErrorCode {

    PRODUCT_CONTENT_CAN_NOT_BE_NULL(HttpStatus.BAD_REQUEST, "제품 내용은 null일 수 없습니다."),
    INVALID_PRODUCT_CONTENT_LENGTH(HttpStatus.BAD_REQUEST, "제품 내용의 길이가 범위를 벗어났습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
