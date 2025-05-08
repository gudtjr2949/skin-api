package com.personal.skin_api.common.redis.service.dto;

import com.personal.skin_api.chat.repository.entity.chat_content.ChatContent;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
public class RedisChat {
    private Long id;
    private String memberNickname;
    private Long chatRoomId;
    private String chatContent;
    private LocalDateTime createdAt;

    @Builder
    private RedisChat(final Long id,
                      final String memberNickname,
                      final Long chatRoomId,
                      final String chatContent,
                      final LocalDateTime createdAt) {
        this.id = id;
        this.memberNickname = memberNickname;
        this.chatRoomId = chatRoomId;
        this.chatContent = chatContent;
        this.createdAt = createdAt;
    }
}
