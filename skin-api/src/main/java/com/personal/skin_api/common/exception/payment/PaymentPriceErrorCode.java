package com.personal.skin_api.common.exception.payment;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentPriceErrorCode implements ErrorCode {
    IMPOSSIBLE_PRICE_RANGE(HttpStatus.BAD_REQUEST, "설정 가능한 금액 범위를 벗어났습니다.");
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
