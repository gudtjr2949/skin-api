package com.personal.skin_api.chat.service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatResponse {
    private Long chatId;
    private String nickname;
    private String chatContent;
    private LocalDateTime createdAt;

    @Builder
    private ChatResponse(final Long chatId,
                         final String nickname,
                         final String chatContent,
                         final LocalDateTime createdAt) {
        this.chatId = chatId;
        this.nickname = nickname;
        this.chatContent = chatContent;
        this.createdAt = createdAt;
    }
}
