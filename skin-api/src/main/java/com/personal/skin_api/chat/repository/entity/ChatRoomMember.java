package com.personal.skin_api.chat.repository.entity;

import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.product.repository.entity.ProductStatus;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "CHAT_ROOM_MEMBER_STATUS")
    private ChatRoomMemberStatus chatRoomMemberStatus;

    @Builder
    private ChatRoomMember(final Member member,
                           final ChatRoom chatRoom) {
        this.member = member;
        this.chatRoom = chatRoom;
        this.chatRoomMemberStatus = ChatRoomMemberStatus.ENTERED;
    }

    public void exitChatRoomMember() {
        this.chatRoomMemberStatus = ChatRoomMemberStatus.EXITED;
    }

    public Long getId() {
        return id;
    }

    public String getChatRoomTitle() {
        return chatRoom.getChatRoomTitle();
    }

    public Long getChatRoomId() {
        return chatRoom.getId();
    }

    public ChatRoomMemberStatus getChatRoomMemberStatus() {
        return chatRoomMemberStatus;
    }
}
