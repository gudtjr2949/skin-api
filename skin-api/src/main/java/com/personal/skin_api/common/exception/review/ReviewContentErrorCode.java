package com.personal.skin_api.common.exception.review;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewContentErrorCode implements ErrorCode {

    REVIEW_CONTENT_CAN_NOT_BE_NULL(HttpStatus.BAD_REQUEST, "댓글 내용은 Null일 수 없습니다."),
    REVIEW_CONTENT_CAN_NOT_BE_EMPTY(HttpStatus.BAD_REQUEST, "댓글 내용은 비어있을 수 없습니다."),
    INVALID_REVIEW_CONTENT_LENGTH(HttpStatus.BAD_REQUEST, "댓글 내용이 범위를 벗어났습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
