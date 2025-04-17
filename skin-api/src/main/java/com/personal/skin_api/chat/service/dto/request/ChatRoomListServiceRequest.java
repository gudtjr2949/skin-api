package com.personal.skin_api.chat.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomListServiceRequest {
    private String email;

    @Builder
    private ChatRoomListServiceRequest(final String email) {
        this.email = email;
    }
}
