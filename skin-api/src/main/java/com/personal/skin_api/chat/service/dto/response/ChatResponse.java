package com.personal.skin_api.chat.service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatResponse {
    private Long chatId;
    private String nickname;
    private String content;

    @Builder
    private ChatResponse(final Long chatId,
                         final String nickname,
                         final String content) {
        this.chatId = chatId;
        this.nickname = nickname;
        this.content = content;
    }
}
