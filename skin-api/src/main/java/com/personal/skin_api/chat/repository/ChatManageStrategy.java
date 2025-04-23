package com.personal.skin_api.chat.repository;

import com.personal.skin_api.chat.repository.entity.ChatRoom;
import com.personal.skin_api.chat.service.dto.response.ChatResponse;
import com.personal.skin_api.member.repository.entity.Member;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatManageStrategy {
    int CHAT_SIZE = 20;
    LocalDateTime save(ChatRoom chatRoom, Member member, String chatContent);
    List<ChatResponse> findChatList(Long chatRoomId, String chatId); // chatId는 마지막으로 조회한 채팅 ID
}
