package com.personal.skin_api.chat.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatListServiceRequest {
    private String chatId;
    private Long chatRoomId;

    @Builder
    private ChatListServiceRequest(final String chatId,
                                   final Long chatRoomId) {
        this.chatId = chatId;
        this.chatRoomId = chatRoomId;
    }
}
