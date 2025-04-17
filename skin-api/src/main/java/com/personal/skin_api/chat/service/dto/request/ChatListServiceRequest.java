package com.personal.skin_api.chat.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatListServiceRequest {
    private Long chatId;
    private Long chatRoomId;
    private String email;

    @Builder
    private ChatListServiceRequest(final Long chatId,
                                   final Long chatRoomId,
                                   final String email) {
        this.chatId = chatId;
        this.chatRoomId = chatRoomId;
        this.email = email;
    }
}
