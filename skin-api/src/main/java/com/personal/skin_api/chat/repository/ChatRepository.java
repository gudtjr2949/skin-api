package com.personal.skin_api.chat.repository;

import com.personal.skin_api.chat.repository.entity.Chat;
import com.personal.skin_api.chat.repository.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findChatByChatRoom(ChatRoom chatRoom);
}
