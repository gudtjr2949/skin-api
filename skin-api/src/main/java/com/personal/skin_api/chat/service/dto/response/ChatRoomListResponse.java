package com.personal.skin_api.chat.service.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ChatRoomListResponse {
    private List<ChatRoomResponse> chatRoomResponses;

    public ChatRoomListResponse(final List<ChatRoomResponse> chatRoomResponses) {
        this.chatRoomResponses = chatRoomResponses;
    }
}
