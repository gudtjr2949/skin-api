package com.personal.skin_api.chat.service;

import com.personal.skin_api.AbstractIntegrationTest;

import com.personal.skin_api.chat.repository.entity.Chat;
import com.personal.skin_api.chat.repository.entity.ChatRoom;
import com.personal.skin_api.chat.repository.entity.ChatRoomMember;
import com.personal.skin_api.chat.repository.entity.ChatRoomMemberStatus;
import com.personal.skin_api.chat.service.dto.request.*;
import com.personal.skin_api.chat.service.dto.response.ChatListResponse;
import com.personal.skin_api.chat.service.dto.response.ChatRoomListResponse;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.product.repository.entity.Product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.personal.skin_api.chat.repository.QChatRepository.*;
import static org.assertj.core.api.Assertions.*;

class ChatServiceTest extends AbstractIntegrationTest {

    @Autowired
    private ChatService chatService;


    @Test
    void 채팅방을_생성한다() {
        // given
        Product product = createProduct(createGeneralMember());
        ChatRoomCreateServiceRequest request = ChatRoomCreateServiceRequest.builder()
                .product(product)
                .build();

        // when
        Long chatRoomId = chatService.createChatRoom(request);

        // then
        assertThat(chatRoomId).isNotNull();
    }

    @Test
    void 채팅방에_접속한다() {
        // given
        Product product = createProduct(createGeneralMember());
        ChatRoom chatRoom = createChatRoom(product);
        ChatRoomEnterServiceRequest request = ChatRoomEnterServiceRequest.builder()
                .chatId(0L)
                .chatRoomId(chatRoom.getId())
                .build();

        // when
        ChatListResponse chatListResponse = chatService.enterChatRoom(request);

        // then
        assertThat(chatListResponse.getChatResponses()).isNotNull();
    }

    @Test
    void 채팅을_전송한다() {
        // given
        Member member = createGeneralMemberWithEmail("sender@naver.com");
        Product product = createProduct(createGeneralMember());
        ChatRoom chatRoom = createChatRoom(product);

        String message = "채팅 메시지입니다.";
        ChatSendServiceRequest request = ChatSendServiceRequest.builder()
                .email(member.getEmail())
                .chatRoomId(chatRoom.getId())
                .chatContent(message)
                .build();

        // when
        chatService.sendChat(request);
        List<Chat> chatByChatRoom = chatRepository.findChatByChatRoom(chatRoom);

        // then
        assertThat(chatByChatRoom).hasSize(1);
        assertThat(chatByChatRoom.get(0).getChatContent()).isEqualTo(message);
    }

    @Test
    void 채팅을_전송한_채팅방이_없다면_예외가_발생한다() {
        // given
        Member member = createGeneralMemberWithEmail("sender@naver.com");
        Product product = createProduct(createGeneralMember());
        ChatRoom chatRoom = createChatRoom(product);

        String message = "채팅 메시지입니다.";
        ChatSendServiceRequest request = ChatSendServiceRequest.builder()
                .email(member.getEmail())
                .chatRoomId(chatRoom.getId()+1)
                .chatContent(message)
                .build();

        // when & then
        assertThatThrownBy(() -> chatService.sendChat(request))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void 채팅방에_접속한_후_최근에_전송된_메시지_리스트를_조회한다() {
        // given
        Member member = createGeneralMemberWithEmail("sender@naver.com");
        Product product = createProduct(createGeneralMember());
        ChatRoom chatRoom = createChatRoom(product);
        int chatCnt = 30;
        IntStream.range(0, chatCnt).forEach(i -> createChat(chatRoom, member, i + "번째 메시지입니다."));

        ChatRoomEnterServiceRequest request = ChatRoomEnterServiceRequest.builder()
                .chatId(0L)
                .chatRoomId(chatRoom.getId())
                .build();

        // when
        ChatListResponse chatListResponse = chatService.enterChatRoom(request);

        // then
        assertThat(chatListResponse.getChatResponses()).hasSize(CHAT_MESSAGE_SIZE);
    }

    @Test
    void 채팅방에_접속한_후_첫_조회_이후_메시지_리스트를_조회한다() {
        // given
        Member member = createGeneralMemberWithEmail("sender@naver.com");
        Product product = createProduct(createGeneralMember());
        ChatRoom chatRoom = createChatRoom(product);
        int chatCnt = 20;

        Chat lastChat = createChat(chatRoom, member, "마지막으로 조회된 채팅입니다.");
        IntStream.range(2, chatCnt).forEach(i -> createChat(chatRoom, member, i + "번째 메시지입니다."));
        Chat firstChat = createChat(chatRoom, member, "처음으로 조회된 채팅입니다.");

        Chat preChat = createChat(chatRoom, member, "가장 최근 조회된 채팅입니다.");
        IntStream.range(0, chatCnt-1).forEach(i -> createChat(chatRoom, member, i + "번째 메시지입니다."));

        ChatRoomEnterServiceRequest request = ChatRoomEnterServiceRequest.builder()
                .chatId(preChat.getId())
                .chatRoomId(chatRoom.getId())
                .build();

        // when
        ChatListResponse chatListResponse = chatService.enterChatRoom(request);

        // then
        assertThat(chatListResponse.getChatResponses().get(0).getChatContent())
                .isEqualTo(firstChat.getChatContent());
        assertThat(chatListResponse.getChatResponses().get(chatListResponse.getChatResponses().size()-1).getChatContent())
                .isEqualTo(lastChat.getChatContent());
    }

    @Test
    void 회원이_접속한_채팅방_목록을_조회한다() {
        // given
        Member member = createGeneralMemberWithEmail("enter@naver.com");
        int chatRoomCnt = 10;

        for (int i = 0 ; i < chatRoomCnt ; i++) {
            Product product = createProduct(createGeneralMember());
            ChatRoom chatRoom = createChatRoom(product);
            createChatRoomMember(chatRoom, member);
        }

        ChatRoomListServiceRequest request = ChatRoomListServiceRequest.builder()
                .email(member.getEmail())
                .build();

        // when
        ChatRoomListResponse myChatRoom = chatService.findMyChatRoom(request);

        // then
        assertThat(myChatRoom.getChatRoomResponses()).hasSize(chatRoomCnt);
    }

    @Test
    void 채팅방에서_퇴장하면_채팅방_회원_엔티티_상태가_변경된다() {
        // given
        Member member = createGeneralMemberWithEmail("sender@naver.com");
        Product product = createProduct(createGeneralMember());
        ChatRoom chatRoom = createChatRoom(product);
        createChatRoomMember(chatRoom, member);

        ChatRoomExitServiceRequest request = ChatRoomExitServiceRequest.builder()
                .chatRoomId(chatRoom.getId())
                .email(member.getEmail())
                .build();

        // when
        chatService.exitChatRoom(request);
        Optional<ChatRoomMember> chatRoomMember = chatRoomMemberRepository.findChatRoomMemberByChatRoomAndMember(chatRoom, member);

        // then
        assertThat(chatRoomMember).isPresent();
        assertThat(chatRoomMember.get().getChatRoomMemberStatus()).isEqualTo(ChatRoomMemberStatus.EXITED);
    }

    @Test
    void 이전에_퇴장한_채팅방은_채팅방_리스트에서_조회되지_않는다() {
        // given
        Member member = createGeneralMemberWithEmail("enter@naver.com");
        int chatRoomCnt = 10;

        for (int i = 0 ; i < chatRoomCnt ; i++) {
            Product product = createProduct(createGeneralMember());
            ChatRoom chatRoom = createChatRoom(product);
            ChatRoomMember chatRoomMember = createChatRoomMember(chatRoom, member);
            if (i % 2 == 0) {
                chatService.exitChatRoom(ChatRoomExitServiceRequest.builder()
                        .email(member.getEmail())
                        .chatRoomId(chatRoom.getId())
                        .build());
            }
        }

        ChatRoomListServiceRequest request = ChatRoomListServiceRequest.builder()
                .email(member.getEmail())
                .build();

        // when
        ChatRoomListResponse myChatRoom = chatService.findMyChatRoom(request);

        // then
        assertThat(myChatRoom.getChatRoomResponses()).hasSize(chatRoomCnt/2);
    }
}