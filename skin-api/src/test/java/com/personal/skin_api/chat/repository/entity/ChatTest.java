package com.personal.skin_api.chat.repository.entity;

import com.personal.skin_api.EntityAbstractIntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ChatTest extends EntityAbstractIntegrationTest {

    @Test
    void 채팅_엔티티를_생성한다() {
        // given
        ChatRoom chatRoom = createChatRoom();
        String message = "채팅입니다.";

        // when
        Chat chat = Chat.builder()
                .chatRoom(chatRoom)
                .member(createGeneralMemberWithEmail("sender@naver.com"))
                .chatContent(message)
                .build();

        // then
        assertThat(chat).isNotNull();
        assertThat(chat.getChatContent()).isEqualTo(message);
    }
}