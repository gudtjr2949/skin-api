package com.personal.skin_api.common.exception.payment;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {
    DUPLICATE_PAYMENT(HttpStatus.BAD_REQUEST, "이미 저장된 결제 정보입니다."),
    CAN_NOT_FOUND_PAYMENT(HttpStatus.NOT_FOUND, "결제정보를 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
