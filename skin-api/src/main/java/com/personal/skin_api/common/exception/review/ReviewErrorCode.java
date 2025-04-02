package com.personal.skin_api.common.exception.review;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements ErrorCode {

    CAN_NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "후기를 찾을 수 없습니다."),
    DUPLICATE_REVIEW(HttpStatus.BAD_REQUEST, "이미 후기를 작성했습니다."),
    NOT_PAYMENT_ORDER(HttpStatus.BAD_REQUEST, "아직 주문에서 결제하지 않았기 때문에 후기를 작성할 수 없습니다."),
    CAN_NOT_MODIFY_REVIEW(HttpStatus.UNAUTHORIZED, "후기를 수정할 권한이 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
