package com.personal.skin_api.chat.repository;

import com.personal.skin_api.chat.repository.entity.ChatRoom;
import com.personal.skin_api.chat.repository.entity.MongoChat;
import com.personal.skin_api.chat.service.dto.response.ChatResponse;
import com.personal.skin_api.member.repository.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MongoChatRepositoryAdapter implements ChatManageStrategy {

    private final MongoChatRepository mongoChatRepository;

    @Override
    public LocalDateTime save(ChatRoom chatRoom, Member member, String chatContent) {
        MongoChat mongoChat = mongoChatRepository.save(MongoChat.builder()
                .memberId(member.getId())
                .memberNickname(member.getNickname())
                .chatRoomId(chatRoom.getId())
                .chatContent(chatContent)
                .build());

        return mongoChat.getCreatedAt();
    }

    @Override
    public List<ChatResponse> findChatList(Long chatRoomId, String chatId) {
        Pageable pageable = PageRequest.of(0, CHAT_SIZE, Sort.by(Sort.Direction.DESC, "id"));

        List<MongoChat> chatList = new ArrayList<>();
        if (chatId.equals("0")) {
            chatList = mongoChatRepository.findChatByChatRoomIdOrderByIdDesc(chatRoomId, pageable);
        } else {
            chatList = mongoChatRepository.findChatByChatRoomIdAndIdLessThanOrderByIdDesc(chatRoomId, chatId, pageable);
        }

        return chatList.stream()
                .map(mongoChat -> ChatResponse.builder()
                        .chatId(mongoChat.getId())
                        .nickname(mongoChat.getMemberNickname())
                        .chatContent(mongoChat.getChatContent())
                        .createdAt(mongoChat.getCreatedAt())
                        .build()
                ).toList();
    }
}
