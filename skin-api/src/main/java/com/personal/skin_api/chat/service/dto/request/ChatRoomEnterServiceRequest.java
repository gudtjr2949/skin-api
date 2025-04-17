package com.personal.skin_api.chat.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomEnterServiceRequest {
    private Long chatRoomId;
    private String email;

    @Builder
    private ChatRoomEnterServiceRequest(final Long chatRoomId,
                                        final String email) {
        this.chatRoomId = chatRoomId;
        this.email = email;
    }
}
