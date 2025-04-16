package com.personal.skin_api.kafka.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KafkaChat {
    private Long chatRoomId;
    private String nickname;
    private String content;

    @Builder
    private KafkaChat(final Long chatRoomId,
                      final String nickname,
                      final String content) {
        this.chatRoomId = chatRoomId;
        this.nickname = nickname;
        this.content = content;
    }
}
