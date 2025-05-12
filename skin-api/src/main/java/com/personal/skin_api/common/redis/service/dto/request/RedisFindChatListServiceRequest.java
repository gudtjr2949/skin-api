package com.personal.skin_api.common.redis.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RedisFindChatListServiceRequest {
    private Long chatId;
    private Long chatRoomId;

    @Builder
    private RedisFindChatListServiceRequest(final Long chatId,
                                            final Long chatRoomId) {
        this.chatId = chatId;
        this.chatRoomId = chatRoomId;
    }
}
