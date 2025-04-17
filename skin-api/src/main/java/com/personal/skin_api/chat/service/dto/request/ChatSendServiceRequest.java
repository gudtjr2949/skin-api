package com.personal.skin_api.chat.service.dto.request;

import com.personal.skin_api.chat.repository.entity.Chat;
import com.personal.skin_api.chat.repository.entity.ChatRoom;
import com.personal.skin_api.member.repository.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatSendServiceRequest {
    private Long chatRoomId;
    private String email;
    private String chatContent;

    @Builder
    private ChatSendServiceRequest(final Long chatRoomId,
                                   final String email,
                                   final String chatContent) {
        this.chatRoomId = chatRoomId;
        this.email = email;
        this.chatContent = chatContent;
    }

    public Chat toEntity(final Member member,
                         final ChatRoom chatRoom) {
        return Chat.builder()
                .member(member)
                .chatRoom(chatRoom)
                .chatContent(chatContent)
                .build();
    }
}
