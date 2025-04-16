package com.personal.skin_api.chat.repository.entity;

import com.personal.skin_api.chat.repository.entity.chat_content.ChatContent;
import com.personal.skin_api.member.repository.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_ROOM_ID")
    private ChatRoom chatRoom;

    @Embedded
    private ChatContent chatContent;

    @Builder
    private Chat(final Member member,
                 final ChatRoom chatRoom,
                 final String chatContent) {
        this.member = member;
        this.chatRoom = chatRoom;
        this.chatContent = new ChatContent(chatContent);
    }

    public Long getId() {
        return id;
    }

    public String getChatContent() {
        return chatContent.getChatContent();
    }
}
