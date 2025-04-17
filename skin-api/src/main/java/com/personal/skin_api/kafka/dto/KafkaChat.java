package com.personal.skin_api.kafka.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KafkaChat {
    private Long chatRoomId;
    private String nickname;
    private String chatContent;

    @Builder
    private KafkaChat(final Long chatRoomId,
                      final String nickname,
                      final String chatContent) {
        this.chatRoomId = chatRoomId;
        this.nickname = nickname;
        this.chatContent = chatContent;
    }
}
