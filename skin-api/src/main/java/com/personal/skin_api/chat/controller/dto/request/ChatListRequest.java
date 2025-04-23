package com.personal.skin_api.chat.controller.dto.request;

import com.personal.skin_api.chat.service.dto.request.ChatListServiceRequest;
import com.personal.skin_api.chat.service.dto.request.ChatRoomEnterServiceRequest;
import lombok.Getter;

@Getter
public class ChatListRequest {
    private Long chatRoomId;
    private String chatId;

    public ChatListServiceRequest toService() {
        return ChatListServiceRequest.builder()
                .chatId(chatId)
                .chatRoomId(chatRoomId)
                .build();
    }
}
