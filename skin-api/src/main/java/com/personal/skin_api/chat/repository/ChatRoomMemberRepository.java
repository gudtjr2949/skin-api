package com.personal.skin_api.chat.repository;

import com.personal.skin_api.chat.repository.entity.ChatRoom;
import com.personal.skin_api.chat.repository.entity.ChatRoomMember;
import com.personal.skin_api.chat.repository.entity.ChatRoomMemberStatus;
import com.personal.skin_api.member.repository.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
    List<ChatRoomMember> findChatRoomMemberByMemberAndChatRoomMemberStatus(Member member,
                                                                            ChatRoomMemberStatus chatRoomMemberStatus);
    Optional<ChatRoomMember> findChatRoomMemberByChatRoomAndMemberAndChatRoomMemberStatus(ChatRoom chatRoom,
                                                                                          Member member,
                                                                                          ChatRoomMemberStatus chatRoomMemberStatus);
}
