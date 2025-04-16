package com.personal.skin_api.chat.repository.entity.chat_content;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class ChatContent {

    @Column(nullable = false,
            columnDefinition = "TEXT",
            name = "CONTENT")
    private String chatContent;

    public ChatContent(final String chatContent) {
        validate(chatContent);
        this.chatContent = chatContent;
    }

    private void validate(final String chatContent) {
        ChatContentStrategyContext.runStrategy(chatContent);
    }

    public String getChatContent() {
        return chatContent;
    }
}
