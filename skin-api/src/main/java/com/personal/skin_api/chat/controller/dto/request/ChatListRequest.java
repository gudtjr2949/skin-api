package com.personal.skin_api.chat.controller.dto.request;

import com.personal.skin_api.chat.service.dto.request.ChatListServiceRequest;
import com.personal.skin_api.chat.service.dto.request.ChatRoomEnterServiceRequest;
import lombok.Getter;

@Getter
public class ChatListRequest {
    private Long chatRoomId;
    private Long chatId;

    public ChatListServiceRequest toService(final String email) {
        return ChatListServiceRequest.builder()
                .chatId(chatId)
                .chatRoomId(chatRoomId)
                .email(email)
                .build();
    }
}
