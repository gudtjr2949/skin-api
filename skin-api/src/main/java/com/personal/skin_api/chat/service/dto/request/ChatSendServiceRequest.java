package com.personal.skin_api.chat.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatSendServiceRequest {
    private Long chatRoomId;
    private String email;
    private String content;

    @Builder
    private ChatSendServiceRequest(final Long chatRoomId,
                                   final String email,
                                   final String content) {
        this.chatRoomId = chatRoomId;
        this.email = email;
        this.content = content;
    }
}
