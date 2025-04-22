package com.personal.skin_api.chat.repository.entity.mongo;

import com.personal.skin_api.chat.repository.entity.chat_content.ChatContent;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@Document(collection = "chats")
public class Chat {

    @Id
    private String id;

    private Long memberId;

    private Long chatRoomId;

    private ChatContent chatContent;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    private Chat(final Long memberId,
                 final Long chatRoomId,
                 final String chatContent) {
        this.memberId = memberId;
        this.chatRoomId = chatRoomId;
        this.chatContent = new ChatContent(chatContent);
    }

    public String getId() {
        return id;
    }

    public String getChatContent() {
        return chatContent.getChatContent();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
