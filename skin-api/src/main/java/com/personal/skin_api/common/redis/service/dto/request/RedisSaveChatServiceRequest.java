package com.personal.skin_api.common.redis.service.dto.request;

import lombok.Builder;
import lombok.Getter;


@Getter
public class RedisSaveChatServiceRequest {
    private Long chatRoomId;
    private String memberNickname;
    private String chatContent;

    @Builder
    private RedisSaveChatServiceRequest(final Long chatRoomId,
                                        final String memberNickname,
                                        final String chatContent) {
        this.chatRoomId = chatRoomId;
        this.memberNickname = memberNickname;
        this.chatContent = chatContent;
    }
}
