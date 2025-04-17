package com.personal.skin_api.chat.service.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ChatListResponse {
    private List<ChatResponse> chatResponses;

    public ChatListResponse(final List<ChatResponse> chatResponses) {
        this.chatResponses = chatResponses;
    }
}
