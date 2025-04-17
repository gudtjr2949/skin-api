package com.personal.skin_api.chat.service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomResponse {
    private Long chatRoomId;
    private String chatRoomTitle;

    @Builder
    private ChatRoomResponse(final Long chatRoomId,
                             final String chatRoomTitle) {
        this.chatRoomId = chatRoomId;
        this.chatRoomTitle = chatRoomTitle;
    }
}
