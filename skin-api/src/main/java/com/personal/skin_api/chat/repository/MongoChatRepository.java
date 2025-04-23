package com.personal.skin_api.chat.repository;

import com.personal.skin_api.chat.repository.entity.MongoChat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoChatRepository extends MongoRepository<MongoChat, String> {
    List<MongoChat> findChatByChatRoomIdOrderByIdDesc(Long chatRoomId, Pageable pageable);
    List<MongoChat> findChatByChatRoomIdAndIdLessThanOrderByIdDesc(Long chatRoomId, String id, Pageable pageable);
}