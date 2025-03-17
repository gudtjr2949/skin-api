package com.personal.skin_api.common.exception.product;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum ProductNameErrorCode implements ErrorCode {

    PRODUCT_NAME_CAN_NOT_BE_NULL(BAD_REQUEST, "제품명이 null일 수 없습니다."),
    INVALID_PRODUCT_NAME_LENGTH(BAD_REQUEST, "제품명 길이가 가능 범위를 벗어났습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}