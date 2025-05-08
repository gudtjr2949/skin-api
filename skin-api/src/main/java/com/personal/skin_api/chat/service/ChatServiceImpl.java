package com.personal.skin_api.chat.service;

import com.personal.skin_api.chat.repository.*;
import com.personal.skin_api.chat.repository.entity.*;
import com.personal.skin_api.chat.service.dto.request.*;
import com.personal.skin_api.chat.service.dto.response.*;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.chat.ChatErrorCode;
import com.personal.skin_api.common.exception.member.MemberErrorCode;
import com.personal.skin_api.kafka.dto.KafkaChat;
import com.personal.skin_api.kafka.producer.KafkaProducer;

import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.email.Email;
import com.personal.skin_api.product.repository.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatServiceImpl implements ChatService {

    private final KafkaProducer kafkaProducer;
    private final MemberRepository memberRepository;
    private final ChatManageContext chatManageContext;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    public static final String STRATEGY_KEY = "redisChatRepositoryAdapter";

    @Override
    @Transactional
    public Long createChatRoom(ChatRoomCreateServiceRequest request) {
        ChatRoom chatRoom = chatRoomRepository.save(request.toEntity());
        return chatRoom.getId();
    }

    @Override
    @Transactional
    public ChatRoomEnterResponse enterChatRoom(ChatRoomEnterServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoomId())
                .orElseThrow(() -> new RestApiException(ChatErrorCode.CAN_NOT_FOUND_CHATROOM));

        if (!chatRoomMemberRepository.
                findChatRoomMemberByChatRoomAndMemberAndChatRoomMemberStatus(chatRoom, member, ChatRoomMemberStatus.ENTERED).isPresent()) {
            chatRoomMemberRepository.save(ChatRoomMember.builder()
                    .member(member)
                    .chatRoom(chatRoom)
                    .build());
        }


        return ChatRoomEnterResponse.builder()
                .sellerNickname(chatRoom.getSellerNickname())
                .myNickname(member.getNickname())
                .chatRoomTitle(chatRoom.getChatRoomTitle())
                .build();
    }

    @Override
    public ChatListResponse findChatList(ChatListServiceRequest request) {
        ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoomId())
                .orElseThrow(() -> new RestApiException(ChatErrorCode.CAN_NOT_FOUND_CHATROOM));

        ChatManageStrategy strategy = chatManageContext.getStrategy(STRATEGY_KEY);
        List<ChatResponse> chatResponses = strategy.findChatList(chatRoom.getId(), request.getChatId());

        return ChatListResponse.builder()
                .chatResponses(chatResponses)
                .build();
    }

    // TODO : 가장 최근 메시지 기록이 있는 채팅방 순으로 정렬
    @Override
    public ChatRoomListResponse findMyChatRoom(ChatRoomListServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        List<ChatRoomMember> chatRoomMembers = chatRoomMemberRepository.findChatRoomMemberByMemberAndChatRoomMemberStatus(member,
                ChatRoomMemberStatus.ENTERED);

        List<ChatRoomResponse> chatRoomResponses = chatRoomMembers.stream()
                .map(chatRoomMember -> ChatRoomResponse.builder()
                        .chatRoomId(chatRoomMember.getChatRoomId())
                        .chatRoomTitle(chatRoomMember.getChatRoomTitle())
                        .build())
                .toList();

        return new ChatRoomListResponse(chatRoomResponses);
    }

    @Override
    @Transactional
    public void sendChat(ChatSendServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoomId())
                .orElseThrow(() -> new RestApiException(ChatErrorCode.CAN_NOT_FOUND_CHATROOM));

        ChatManageStrategy strategy = chatManageContext.getStrategy(STRATEGY_KEY);
        LocalDateTime createdAt = strategy.save(chatRoom, member, request.getChatContent());

        KafkaChat kafkaChat = new KafkaChat(
                chatRoom.getId(),
                member.getNickname(),
                request.getChatContent(),
                createdAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        );

        kafkaProducer.sendMessage("chat-exchange", kafkaChat);
    }

    @Override
    @Transactional
    public void exitChatRoom(ChatRoomExitServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoomId())
                .orElseThrow(() -> new RestApiException(ChatErrorCode.CAN_NOT_FOUND_CHATROOM));

        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findChatRoomMemberByChatRoomAndMemberAndChatRoomMemberStatus(chatRoom, member, ChatRoomMemberStatus.ENTERED)
                .orElseThrow(() -> new RestApiException(ChatErrorCode.CAN_NOT_FOUND_CHAT_ROOM_MEMBER));

        chatRoomMember.exitChatRoomMember();
    }

    @Override
    @Transactional
    public void deleteChatRoom(Product product) {
        ChatRoom chatRoom = chatRoomRepository.findByProduct(product)
                .orElseThrow(() -> new RestApiException(ChatErrorCode.CAN_NOT_FOUND_CHATROOM));

        chatRoom.deleteChatRoom();
    }
}
