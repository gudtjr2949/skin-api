package com.personal.skin_api.chat.repository;

import com.personal.skin_api.chat.repository.entity.MongoChat;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DataMongoTest
class MongoChatRepositoryTest {

    @Autowired
    private MongoChatRepository mongoChatRepository;

    @AfterEach
    void tearDown() {
        mongoChatRepository.deleteAll();
    }

    @Test
    void 채팅을_저장한_후_조회한다() {
        // given
        Long memberId = 1L;
        Long chatRoomId = 1L;
        String chatContent = "채팅입니다.";
        MongoChat chat = MongoChat.builder()
                .memberId(memberId)
                .chatRoomId(chatRoomId)
                .chatContent(chatContent)
                .build();

        // when
        MongoChat saveedChat = mongoChatRepository.save(chat);
        Optional<MongoChat> byId = mongoChatRepository.findById(saveedChat.getId());

        // then
        assertThat(byId).isPresent();
        assertThat(byId.get().getId()).isEqualTo(saveedChat.getId());
    }

    // TODO : 서비스 단 구현 시, 한 페이지당 채팅 갯수 상수 필요함!! 현재는 매직넘버로 구현된 상태!!
    @Test
    void 최근에_입력된_채팅을_조회한다() {
        // given
        Long memberId = 1L;
        Long chatRoomId = 1L;
        String lastWatchedChatId = null;
        String firstChatContent = "";
        String lastChatContent = "";

        for (int i = 0 ; i < 30 ; i++) {
            String chatContent = i + " 번째 채팅입니다.";
            MongoChat chat = MongoChat.builder()
                    .memberId(memberId)
                    .chatRoomId(chatRoomId)
                    .chatContent(chatContent)
                    .build();

            MongoChat save = mongoChatRepository.save(chat);

            if (i == 20) {
                lastWatchedChatId = save.getId();
            } else if (i == 19) {
                firstChatContent = save.getChatContent();
            } else if (i == 0) {
                lastChatContent = save.getChatContent();
            }
        }

        // when
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "id"));
        List<MongoChat> chats = mongoChatRepository.findChatByChatRoomIdAndIdLessThanOrderByIdDesc(chatRoomId, lastWatchedChatId, pageable);

        // then
        assertThat(chats).hasSize(20);
        assertThat(chats.get(0).getChatContent()).isEqualTo(firstChatContent);
        assertThat(chats.get(19).getChatContent()).isEqualTo(lastChatContent);
    }

}