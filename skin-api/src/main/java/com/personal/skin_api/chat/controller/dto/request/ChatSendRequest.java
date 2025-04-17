package com.personal.skin_api.chat.controller.dto.request;

import com.personal.skin_api.chat.service.dto.request.ChatSendServiceRequest;
import lombok.Getter;

@Getter
public class ChatSendRequest {

    private Long chatRoomId;
    private String chatContent;

    public ChatSendServiceRequest toService(final String email) {
        return ChatSendServiceRequest.builder()
                .email(email)
                .chatRoomId(chatRoomId)
                .chatContent(chatContent)
                .build();
    }
}
