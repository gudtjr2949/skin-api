package com.personal.skin_api.common.exception.order;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

    CAN_NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없습니다."),
    INVALID_ORDER_MEMBER(HttpStatus.NOT_FOUND, "주문정보의 회원 정보와 입력하신 회원정보가 일치하지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
