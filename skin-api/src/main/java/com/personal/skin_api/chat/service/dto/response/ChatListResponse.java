package com.personal.skin_api.chat.service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ChatListResponse {

    private List<ChatResponse> chatResponses;

    @Builder
    private ChatListResponse(final List<ChatResponse> chatResponses) {
        this.chatResponses = chatResponses;
    }
}
