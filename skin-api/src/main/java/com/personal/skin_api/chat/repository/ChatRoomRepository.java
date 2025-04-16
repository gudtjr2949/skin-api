package com.personal.skin_api.chat.repository;

import com.personal.skin_api.chat.repository.entity.ChatRoom;
import com.personal.skin_api.member.repository.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}