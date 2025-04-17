package com.personal.skin_api.chat.repository;

import com.personal.skin_api.chat.repository.entity.Chat;
import com.personal.skin_api.chat.repository.entity.QChat;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.order.repository.entity.QOrder;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QChatRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    public static final int CHAT_MESSAGE_SIZE = 20; // 한번에 20개의 메시지 조회

    public QChatRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Chat> findChatList(Long chatId, Long chatRoomId) {
        BooleanBuilder builder = new BooleanBuilder();

        if (chatId > 0) {
            builder.and(QChat.chat.id.lt(chatId));
        }

        builder.and(QChat.chat.chatRoom.id.eq(chatRoomId));

        List<Chat> findChats = queryFactory
                .selectFrom(QChat.chat)
                .where(builder)
                .orderBy(QChat.chat.id.desc())
                .limit(CHAT_MESSAGE_SIZE)
                .fetch();

        return findChats;
    }
}
