package com.personal.skin_api.chat.repository;

import com.personal.skin_api.chat.repository.entity.ChatRoomMember;
import com.personal.skin_api.member.repository.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
    List<ChatRoomMember> findChatRoomMemberByMember(Member member);
}
