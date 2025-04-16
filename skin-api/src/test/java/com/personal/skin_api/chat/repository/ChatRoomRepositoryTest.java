package com.personal.skin_api.chat.repository;

import com.personal.skin_api.JpaAbstractIntegrationTest;
import com.personal.skin_api.chat.repository.entity.ChatRoom;
import com.personal.skin_api.member.repository.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

class ChatRoomRepositoryTest extends JpaAbstractIntegrationTest {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    void 채팅방을_생성한다() {
        // given
        ChatRoom chatRoom = ChatRoom.builder()
                .product(createProduct(createGeneralMember()))
                .build();

        // when
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        // then
        assertThat(savedChatRoom.getId()).isEqualTo(chatRoom.getId());
    }
}