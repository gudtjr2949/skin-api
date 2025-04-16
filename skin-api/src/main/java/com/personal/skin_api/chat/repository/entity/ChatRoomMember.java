package com.personal.skin_api.chat.repository.entity;

import com.personal.skin_api.member.repository.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ChatRoomMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "CHATROOM_ID")
    private ChatRoom chatRoom;

    @Builder
    private ChatRoomMember(final Member member,
                           final ChatRoom chatRoom) {
        this.member = member;
        this.chatRoom = chatRoom;
    }
}
