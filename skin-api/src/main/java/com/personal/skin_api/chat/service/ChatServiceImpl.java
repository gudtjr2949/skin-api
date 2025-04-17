package com.personal.skin_api.chat.service;

import com.personal.skin_api.chat.repository.ChatRepository;
import com.personal.skin_api.chat.repository.ChatRoomMemberRepository;
import com.personal.skin_api.chat.repository.ChatRoomRepository;
import com.personal.skin_api.chat.repository.QChatRepository;
import com.personal.skin_api.chat.repository.entity.Chat;
import com.personal.skin_api.chat.repository.entity.ChatRoom;
import com.personal.skin_api.chat.repository.entity.ChatRoomMember;
import com.personal.skin_api.chat.repository.entity.ChatRoomMemberStatus;
import com.personal.skin_api.chat.service.dto.request.*;
import com.personal.skin_api.chat.service.dto.response.ChatListResponse;
import com.personal.skin_api.chat.service.dto.response.ChatResponse;
import com.personal.skin_api.chat.service.dto.response.ChatRoomListResponse;
import com.personal.skin_api.chat.service.dto.response.ChatRoomResponse;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.chat.ChatErrorCode;
import com.personal.skin_api.common.exception.member.MemberErrorCode;
import com.personal.skin_api.kafka.dto.KafkaChat;
import com.personal.skin_api.kafka.producer.KafkaProducer;

import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.email.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatServiceImpl implements ChatService {

    private final KafkaProducer kafkaProducer;
    private final ChatRepository chatRepository;
    private final QChatRepository qChatRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @Override
    @Transactional
    public Long createChatRoom(ChatRoomCreateServiceRequest request) {
        ChatRoom chatRoom = chatRoomRepository.save(request.toEntity());
        return chatRoom.getId();
    }

    @Override
    @Transactional
    public ChatListResponse enterChatRoom(ChatRoomEnterServiceRequest request) {
        List<Chat> chatList = qChatRepository.findChatList(request.getChatId(), request.getChatRoomId());

        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoomId())
                .orElseThrow(() -> new RestApiException(ChatErrorCode.CAN_NOT_FOUND_CHATROOM));

        chatRoomMemberRepository.save(ChatRoomMember.builder()
                .member(member)
                .chatRoom(chatRoom)
                .build());

        List<ChatResponse> chatResponses = chatList.stream()
                .map(chat -> ChatResponse.builder()
                        .chatId(chat.getId())
                        .nickname(chat.getNickname())
                        .chatContent(chat.getChatContent())
                        .build())
                .toList();

        return new ChatListResponse(chatResponses);
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

        chatRepository.save(request.toEntity(member, chatRoom));

        KafkaChat kafkaChat = KafkaChat.builder()
                .chatRoomId(chatRoom.getId())
                .nickname(member.getNickname())
                .chatContent(request.getChatContent())
                .build();

        kafkaProducer.sendMassage("chat-exchange", kafkaChat);
    }

    @Override
    @Transactional
    public void exitChatRoom(ChatRoomExitServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoomId())
                .orElseThrow(() -> new RestApiException(ChatErrorCode.CAN_NOT_FOUND_CHATROOM));

        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findChatRoomMemberByChatRoomAndMember(chatRoom, member)
                .orElseThrow(() -> new RestApiException(ChatErrorCode.CAN_NOT_FOUND_CHAT_ROOM_MEMBER));

        chatRoomMember.exitChatRoomMember();
    }
}
