package com.personal.skin_api.common.redis.service.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jdk8.LongStreamSerializer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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
