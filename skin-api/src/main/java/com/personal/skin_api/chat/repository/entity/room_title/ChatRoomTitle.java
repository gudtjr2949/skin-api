package com.personal.skin_api.chat.repository.entity.room_title;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class ChatRoomTitle {

    @Column(name = "CHAT_ROOM_TITLE")
    private String chatRoomTitle;

    public static final String LEFT_BRACKET = "[",
            RIGHT_BRACKET = "]",
            TITLE_SUFFIX = " 채팅방";

    public ChatRoomTitle(final String productName) {
        this.chatRoomTitle = generateChatRoomTitle(productName);
    }

    private String generateChatRoomTitle(final String productName) {
        return String.format("%s%s%s%s", LEFT_BRACKET, productName, RIGHT_BRACKET, TITLE_SUFFIX);
    }

    public String getChatRoomTitle() {
        return chatRoomTitle;
    }
}
