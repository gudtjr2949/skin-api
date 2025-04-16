package com.personal.skin_api.common.exception.chat;

import com.personal.skin_api.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChatErrorCode implements ErrorCode {
    CAN_NOT_FOUND_CHATROOM(HttpStatus.NOT_FOUND, "채팅방을 찾을 수 없습니다."),
    CHAT_CONTENT_CAN_NOT_BE_NULL(HttpStatus.BAD_REQUEST, "채팅 메시지는 NULL일 수 없습니다."),
    CHAT_CONTENT_CAN_NOT_BE_BLANK(HttpStatus.BAD_REQUEST, "채팅 메시지는 NULL일 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}