package com.personal.skin_api.chat.repository.entity.mongo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoChatRepository extends MongoRepository<Chat, String> {
    List<Chat> findChatByChatRoomIdAndIdLessThanOrderByIdDesc(Long chatRoomId, String id, Pageable pageable);
}
