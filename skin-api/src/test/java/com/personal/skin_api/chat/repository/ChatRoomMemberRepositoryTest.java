package com.personal.skin_api.chat.repository;

import com.personal.skin_api.JpaAbstractIntegrationTest;
import com.personal.skin_api.chat.repository.entity.ChatRoom;
import com.personal.skin_api.chat.repository.entity.ChatRoomMember;
import com.personal.skin_api.chat.repository.entity.ChatRoomMemberStatus;
import com.personal.skin_api.member.repository.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class ChatRoomMemberRepositoryTest extends JpaAbstractIntegrationTest {

    @Autowired
    private ChatRoomMemberRepository chatRoomMemberRepository;

    @Test
    void 회원이_채팅방에_입장한다() {
        // given
        Member member = createGeneralMemberWithEmail("enter@naver.com");
        ChatRoom chatRoom = createChatRoom();

        ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                .member(member)
                .chatRoom(chatRoom)
                .build();

        // when
        ChatRoomMember savedChatRoomMember = chatRoomMemberRepository.save(chatRoomMember);

        // then
        assertThat(savedChatRoomMember.getId()).isEqualTo(chatRoomMember.getId());
    }

    @Test
    void 회원이_채팅방에서_퇴장한다() {
        // given
        Member member = createGeneralMemberWithEmail("enter@naver.com");
        ChatRoom chatRoom = createChatRoom();

        ChatRoomMember chatRoomMember = ChatRoomMember.builder()
                .member(member)
                .chatRoom(chatRoom)
                .build();

        ChatRoomMember savedChatRoomMember = chatRoomMemberRepository.save(chatRoomMember);

        // when
        chatRoomMemberRepository.delete(savedChatRoomMember);
        Optional<ChatRoomMember> deletedChatRoomMember = chatRoomMemberRepository.findById(savedChatRoomMember.getId());

        // then
        assertThat(deletedChatRoomMember).isNotPresent();
    }

    @Test
    void 회원을_토대로_입장된_채팅방_리스트를_조회한다() {
        // given
        Member member = createGeneralMemberWithEmail("enter@naver.com");
        int enteredChatRoomCnt = 5;

        for (int i = 0 ; i < enteredChatRoomCnt ; i++) {
            ChatRoom chatRoom = createChatRoom();
            createChatRoomMember(member, chatRoom);
        }

        // when
        List<ChatRoomMember> chatRoomList = chatRoomMemberRepository.findChatRoomMemberByMemberAndChatRoomMemberStatus(member,
                ChatRoomMemberStatus.ENTERED);

        // then
        assertThat(chatRoomList).hasSize(enteredChatRoomCnt);
    }
}