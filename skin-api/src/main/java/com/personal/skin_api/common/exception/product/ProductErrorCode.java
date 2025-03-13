package com.personal.skin_api.common.exception.product;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements ErrorCode {


    ;

    private final HttpStatus httpStatus;
    private final String message;
}
