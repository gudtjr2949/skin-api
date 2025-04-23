package com.personal.skin_api.chat.repository;

import com.personal.skin_api.chat.repository.entity.Chat;
import com.personal.skin_api.chat.repository.entity.ChatRoom;
import com.personal.skin_api.chat.service.dto.response.ChatResponse;
import com.personal.skin_api.member.repository.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaChatRepositoryAdapter implements ChatManageStrategy {

    private final ChatRepository chatRepository;
    private final QChatRepository qChatRepository;

    @Override
    public LocalDateTime save(ChatRoom chatRoom, Member member, String chatContent) {
        Chat chat = chatRepository.save(Chat.builder()
                .chatRoom(chatRoom)
                .member(member)
                .chatContent(chatContent)
                .build());

        return chat.getCreatedAt();
    }

    @Override
    public List<ChatResponse> findChatList(Long chatRoomId, String chatId) {
        List<Chat> chatList = qChatRepository.findChatList(Long.parseLong(chatId), chatRoomId);

        return chatList.stream()
                .map(chat -> ChatResponse.builder()
                        .chatId(Long.toString(chat.getId()))
                        .nickname(chat.getNickname())
                        .chatContent(chat.getChatContent())
                        .createdAt(chat.getCreatedAt())
                        .build())
                .toList();
    }
}
