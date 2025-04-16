package com.personal.skin_api.chat.repository;

import com.personal.skin_api.JpaAbstractIntegrationTest;
import com.personal.skin_api.chat.repository.entity.Chat;
import com.personal.skin_api.chat.repository.entity.ChatRoom;
import com.personal.skin_api.member.repository.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ChatRepositoryTest extends JpaAbstractIntegrationTest {

    @Autowired
    private ChatRepository chatRepository;


    @Test
    void 채팅을_저장한다() {
        // given
        Member member = createGeneralMemberWithEmail("enter@naver.com");
        ChatRoom chatRoom = createChatRoom();
        Chat chat = Chat.builder()
                .chatRoom(chatRoom)
                .member(member)
                .chatContent("채팅 내용입니다.")
                .build();

        // when
        Chat savedChat = chatRepository.save(chat);

        // then
        assertThat(savedChat.getId()).isEqualTo(chat.getId());
    }

    @Test
    void 채팅방_정보를_토대로_채팅_리스트를_조회한다() {
        // given
        Member member = createGeneralMemberWithEmail("enter@naver.com");
        ChatRoom chatRoom = createChatRoom();
        int chatCnt = 5;

        for (int i = 0 ; i < chatCnt ; i++) {
            chatRepository.save(Chat.builder()
                    .chatRoom(chatRoom)
                    .member(member)
                    .chatContent("채팅 내용입니다.")
                    .build());
        }

        // when
        List<Chat> chatList = chatRepository.findChatByChatRoom(chatRoom);

        // then
        assertThat(chatList).hasSize(chatCnt);
    }
}