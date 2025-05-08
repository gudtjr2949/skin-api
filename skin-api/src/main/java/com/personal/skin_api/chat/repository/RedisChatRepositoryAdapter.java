package com.personal.skin_api.chat.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.skin_api.chat.repository.entity.ChatRoom;
import com.personal.skin_api.chat.service.dto.response.ChatResponse;
import com.personal.skin_api.common.redis.service.RedisService;
import com.personal.skin_api.common.redis.service.dto.RedisChat;
import com.personal.skin_api.common.redis.service.dto.request.RedisFindChatListServiceRequest;
import com.personal.skin_api.common.redis.service.dto.request.RedisSaveChatServiceRequest;
import com.personal.skin_api.member.repository.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisChatRepositoryAdapter implements ChatManageStrategy {

    private final RedisService redisService;

    @Override
    public LocalDateTime save(ChatRoom chatRoom, Member member, String chatContent) {
        RedisSaveChatServiceRequest request = RedisSaveChatServiceRequest.builder()
                .chatRoomId(chatRoom.getId())
                .memberNickname(member.getNickname())
                .chatContent(chatContent)
                .build();
        LocalDateTime createdAt = redisService.saveChat(request);
        return createdAt;
    }

    @Override
    public List<ChatResponse> findChatList(Long chatRoomId, String chatId) {
        RedisFindChatListServiceRequest request = RedisFindChatListServiceRequest.builder()
                .chatRoomId(chatRoomId)
                .chatId(Long.parseLong(chatId))
                .build();

        List<RedisChat> chatList = redisService.findChatList(request);

        return chatList.stream()
                .map(chat -> ChatResponse.builder()
                        .chatId(Long.toString(chat.getId()))
                        .nickname(chat.getMemberNickname())
                        .chatContent(chat.getChatContent())
                        .createdAt(chat.getCreatedAt())
                        .build())
                .toList();
    }
}
