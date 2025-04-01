package com.personal.skin_api.common.exception.payment;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ImpUidErrorCode implements ErrorCode {
    IMP_UID_CAN_NOT_BE_NULL(HttpStatus.BAD_REQUEST, "ImpUid는 Null일 수 없습니다"),
    INVALID_IMP_UID_FORMAT(HttpStatus.BAD_REQUEST, "ImpUid의 형태가 올바르지 않습니다."),
    INVALID_IMP_UID_LENGTH(HttpStatus.BAD_REQUEST, "ImpUid의 길이가 올바르지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
