package com.personal.skin_api.chat.repository.entity;

import com.personal.skin_api.EntityAbstractIntegrationTest;
import com.personal.skin_api.member.repository.entity.Member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ChatRoomMemberTest extends EntityAbstractIntegrationTest {

    @Test
    void 회원이_채팅방에_입장한다() {
        // given
        Member member = createGeneralMemberWithEmail("enter@naver.com");
        ChatRoom chatRoom = createChatRoom();

        // when
        ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                .member(member)
                .chatRoom(chatRoom)
                .build();

        // then
        assertThat(chatRoomMember).isNotNull();
    }

}