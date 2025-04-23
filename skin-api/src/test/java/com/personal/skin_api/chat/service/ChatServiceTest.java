package com.personal.skin_api.chat.service;

import com.personal.skin_api.AbstractIntegrationTest;

import com.personal.skin_api.chat.repository.entity.*;
import com.personal.skin_api.chat.service.dto.request.*;
import com.personal.skin_api.chat.service.dto.response.ChatListResponse;
import com.personal.skin_api.chat.service.dto.response.ChatResponse;
import com.personal.skin_api.chat.service.dto.response.ChatRoomListResponse;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.product.repository.entity.Product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.personal.skin_api.chat.repository.ChatManageStrategy.CHAT_SIZE;
import static com.personal.skin_api.chat.repository.QChatRepository.*;
import static com.personal.skin_api.chat.service.ChatServiceImpl.STRATEGY_KEY;
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
    void 채팅방에_입장한다() {
        // given
        Member member = createGeneralMemberWithEmail("sender@naver.com");
        Product product = createProduct(createGeneralMember());
        ChatRoom chatRoom = createChatRoom(product);
        ChatRoomEnterServiceRequest request = ChatRoomEnterServiceRequest.builder()
                .email(member.getEmail())
                .chatRoomId(chatRoom.getId())
                .build();

        // when
        chatService.enterChatRoom(request);
        Optional<ChatRoomMember> chatRoomMember = chatRoomMemberRepository.findChatRoomMemberByChatRoomAndMemberAndChatRoomMemberStatus(chatRoom, member, ChatRoomMemberStatus.ENTERED);

        // then
        assertThat(chatRoomMember).isPresent();
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

        // when & then
        assertThatNoException().isThrownBy(() -> chatService.sendChat(request));
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
    void 채팅_리스트를_조회한다() {
        // given
        Member member = createGeneralMemberWithEmail("sender@naver.com");
        Product product = createProduct(createGeneralMember());
        ChatRoom chatRoom = createChatRoom(product);
        String lastFindMessage = "1번째 메시지 입니다.";
        chatService.sendChat(createChatSendRequest(member, chatRoom, lastFindMessage));
        IntStream.range(2, CHAT_SIZE).forEach(i -> chatService.sendChat(createChatSendRequest(member, chatRoom, i + "번째 메시지입니다.")));
        String firstFindMessage = "20번째 메시지입니다.";
        chatService.sendChat(createChatSendRequest(member, chatRoom, firstFindMessage));

        String preChatId = saveChat(member, chatRoom, "가장 최근에 조회된 메시지");

        ChatListServiceRequest request = ChatListServiceRequest.builder()
                .chatRoomId(chatRoom.getId())
                .chatId(preChatId)
                .build();

        // when
        ChatListResponse chatListResponse = chatService.findChatList(request);
        ChatResponse firstChat = chatListResponse.getChatResponses().get(0);
        ChatResponse lastChat = chatListResponse.getChatResponses().get(CHAT_SIZE-1);

        // then
        assertThat(chatListResponse.getChatResponses()).hasSize(CHAT_SIZE);
        assertThat(firstChat.getChatContent()).isEqualTo(firstFindMessage);
        assertThat(lastChat.getChatContent()).isEqualTo(lastFindMessage);
    }
    
    @Test
    void 채팅_리스트_마지막_페이지는_채팅수가_기준보다_적을_수_있다() {
        // given
        Member member = createGeneralMemberWithEmail("sender@naver.com");
        Product product = createProduct(createGeneralMember());
        ChatRoom chatRoom = createChatRoom(product);
        int chatSize = CHAT_SIZE - 10;
        String lastFindMessage = "마지막으로 조회되는 메시지 입니다.";
        chatService.sendChat(createChatSendRequest(member, chatRoom, lastFindMessage));
        IntStream.range(2, chatSize).forEach(i -> chatService.sendChat(createChatSendRequest(member, chatRoom, i + "번째 메시지입니다.")));
        String firstFindMessage = "처음으로 조회되는 메시지입니다.";
        chatService.sendChat(createChatSendRequest(member, chatRoom, firstFindMessage));

        String preChatId = saveChat(member, chatRoom, "가장 최근에 조회된 메시지");

        ChatListServiceRequest request = ChatListServiceRequest.builder()
                .chatRoomId(chatRoom.getId())
                .chatId(preChatId)
                .build();

        // when
        ChatListResponse chatListResponse = chatService.findChatList(request);
        ChatResponse firstChat = chatListResponse.getChatResponses().get(0);
        ChatResponse lastChat = chatListResponse.getChatResponses().get(chatListResponse.getChatResponses().size()-1);

        // then
        assertThat(chatListResponse.getChatResponses()).hasSizeLessThan(CHAT_SIZE);
        assertThat(firstChat.getChatContent()).isEqualTo(firstFindMessage);
        assertThat(lastChat.getChatContent()).isEqualTo(lastFindMessage);
    }

    private String saveChat(Member member, ChatRoom chatRoom, String chatContent) {
        if (STRATEGY_KEY.equals("mongoChatRepositoryAdapter")) {
            return mongoChatRepository.save(MongoChat.builder()
                    .chatRoomId(chatRoom.getId())
                    .memberId(member.getId())
                    .memberNickname(member.getNickname())
                    .chatContent(chatContent)
                    .build()).getId();
        } else {
            return Long.toString(chatRepository.save(Chat.builder()
                    .member(member)
                    .chatRoom(chatRoom)
                    .chatContent(chatContent)
                    .build()).getId());
        }
    }

    private ChatSendServiceRequest createChatSendRequest(Member member, ChatRoom chatRoom, String chatContent) {
        return ChatSendServiceRequest.builder()
                .email(member.getEmail())
                .chatRoomId(chatRoom.getId())
                .chatContent(chatContent)
                .build();
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
        Optional<ChatRoomMember> chatRoomMember = chatRoomMemberRepository.findChatRoomMemberByChatRoomAndMemberAndChatRoomMemberStatus(
                chatRoom,
                member,
                ChatRoomMemberStatus.EXITED);

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