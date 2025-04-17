package com.personal.skin_api.chat.service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatResponse {
    private Long chatId;
    private String nickname;
    private String chatContent;

    @Builder
    private ChatResponse(final Long chatId,
                         final String nickname,
                         final String chatContent) {
        this.chatId = chatId;
        this.nickname = nickname;
        this.chatContent = chatContent;
    }
}
