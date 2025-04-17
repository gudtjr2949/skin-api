package com.personal.skin_api.chat.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomExitServiceRequest {
    private Long chatRoomId;
    private String email;

    @Builder
    private ChatRoomExitServiceRequest(final Long chatRoomId,
                                       final String email) {
        this.chatRoomId = chatRoomId;
        this.email = email;
    }
}
