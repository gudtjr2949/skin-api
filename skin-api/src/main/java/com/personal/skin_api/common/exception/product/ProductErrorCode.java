package com.personal.skin_api.common.exception.product;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements ErrorCode {

    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "제품을 찾을 수 없습니다."),
    CAN_NOT_MODIFY_PRODUCT(BAD_REQUEST, "해당 제품을 수정할 권한이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
