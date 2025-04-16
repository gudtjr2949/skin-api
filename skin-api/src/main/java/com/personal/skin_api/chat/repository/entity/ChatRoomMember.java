package com.personal.skin_api.chat.repository.entity;

import com.personal.skin_api.member.repository.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ChatRoomMember {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "CHAT_ROOM_ID")
    private ChatRoom chatRoom;

    @Builder
    private ChatRoomMember(final Member member,
                           final ChatRoom chatRoom) {
        this.member = member;
        this.chatRoom = chatRoom;
    }

    public Long getId() {
        return id;
    }

    // TODO : 아마 chatRoomId랑 chatRoomTitle 정보 리턴 필요할 듯..?
}
