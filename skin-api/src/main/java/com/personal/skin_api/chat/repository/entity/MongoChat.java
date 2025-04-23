package com.personal.skin_api.chat.repository.entity;

import com.personal.skin_api.chat.repository.entity.chat_content.ChatContent;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Document(collection = "chats")
public class MongoChat {

    @Id
    private String id;

    private Long memberId;

    private String memberNickname;

    private Long chatRoomId;

    private ChatContent chatContent;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    private MongoChat(final Long memberId,
                      final String memberNickname,
                      final Long chatRoomId,
                      final String chatContent) {
        this.memberId = memberId;
        this.memberNickname = memberNickname;
        this.chatRoomId = chatRoomId;
        this.chatContent = new ChatContent(chatContent);
    }

    public String getId() {
        return id;
    }

    public String getChatContent() {
        return chatContent.getChatContent();
    }

    public String getMemberNickname() {
        return memberNickname;
    }

    public Long getChatRoomId() {
        return chatRoomId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
