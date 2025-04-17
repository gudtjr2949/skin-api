package com.personal.skin_api.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum KafkaErrorCode implements ErrorCode {

    KAFKA_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "메시지 전송 과정 중 Kafka 서버에서 에러가 발생했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
