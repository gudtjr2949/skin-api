package com.personal.skin_api.chat.service.dto.request;

import lombok.Builder;

public class ChatRoomEnterServiceRequest {
    private Long chatRoomId;

    @Builder
    private ChatRoomEnterServiceRequest(final Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
}
