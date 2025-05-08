package com.personal.skin_api.common.redis.service;

import com.personal.skin_api.AbstractIntegrationTest;
import com.personal.skin_api.chat.repository.entity.ChatRoom;
import com.personal.skin_api.common.redis.service.dto.RedisChat;
import com.personal.skin_api.common.redis.service.dto.request.RedisFindChatListServiceRequest;
import com.personal.skin_api.common.redis.service.dto.request.RedisFindMailCertServiceRequest;
import com.personal.skin_api.common.redis.service.dto.request.RedisSaveChatServiceRequest;
import com.personal.skin_api.common.redis.service.dto.request.RedisSaveMailCertServiceRequest;
import com.personal.skin_api.common.util.CertCodeGenerator;

import com.personal.skin_api.common.redis.MailPurpose;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.product.repository.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static com.personal.skin_api.chat.repository.ChatManageStrategy.CHAT_SIZE;
import static org.assertj.core.api.Assertions.*;

class RedisServiceTest extends AbstractIntegrationTest {

    @Autowired
    private RedisService redisService;

    @Autowired
    private CertCodeGenerator codeGenerator;

    @Test
    void 이메일_인증용_인증코드를_Redis에_저장하고_조회한다() {
        // given
        String code = codeGenerator.createCertCodeAtMail();
        String email = "asd123@naver.com";
        RedisSaveMailCertServiceRequest saveMailRequest = RedisSaveMailCertServiceRequest.builder()
                .purpose(MailPurpose.CHECK_EMAIL)
                .email(email)
                .code(code)
                .build();

        RedisFindMailCertServiceRequest findMailRequest = RedisFindMailCertServiceRequest.builder()
                .purpose(MailPurpose.CHECK_EMAIL)
                .email(email)
                .build();

        // when
        redisService.saveMailCertification(saveMailRequest);
        String findCertificationCode = redisService.findMailCertification(findMailRequest);

        // then
        assertThat(findCertificationCode).isEqualTo(code);
    }

    @Test
    void Redis_INCR을_사용해_고유한_메시지_ID를_만든다() {
        // given
        redisTemplate.delete("chat:message:id:seq"); // 초기화 (테스트 격리용)

        // when
        Long id1 = redisTemplate.opsForValue().increment("chat:message:id:seq");
        Long id2 = redisTemplate.opsForValue().increment("chat:message:id:seq");

        // then
        assertThat(id1).isEqualTo(1L);
        assertThat(id2).isEqualTo(2L);
    }

    @Test
    void 채팅을_저장한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);
        ChatRoom chatRoom = createChatRoom(product);
        String chatContent = "채팅 테스트";
        RedisSaveChatServiceRequest request = RedisSaveChatServiceRequest.builder()
                .chatRoomId(chatRoom.getId())
                .memberNickname(member.getNickname())
                .chatContent(chatContent)
                .build();

        // when
        LocalDateTime createdAt = redisService.saveChat(request);

        // then
        assertThat(createdAt).isNotNull();
    }

    @Test
    void 채팅_리스트를_조회한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);
        ChatRoom chatRoom = createChatRoom(product);
        String chatContent = "채팅 테스트";
        RedisSaveChatServiceRequest sendChatRequest = RedisSaveChatServiceRequest.builder()
                .chatRoomId(chatRoom.getId())
                .memberNickname(member.getNickname())
                .chatContent(chatContent)
                .build();

        int chatCnt = CHAT_SIZE;
        IntStream.range(0, chatCnt).forEach(i -> redisService.saveChat(sendChatRequest));
        RedisFindChatListServiceRequest findChatRequest = RedisFindChatListServiceRequest.builder()
                .chatId(0L)
                .chatRoomId(chatRoom.getId())
                .build();

        // when
        List<RedisChat> chatList = redisService.findChatList(findChatRequest);

        // then
        assertThat(chatList).hasSize(chatCnt);
    }

    @Test
    void 가장_최근에_조회한_채팅_이전_채팅_리스트가_조회된다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);
        ChatRoom chatRoom = createChatRoom(product);
        String chatContent = "채팅 테스트";

        int chatCnt = CHAT_SIZE+10;
        IntStream.range(0, chatCnt).forEach(i -> redisService.saveChat(RedisSaveChatServiceRequest.builder()
                .chatRoomId(chatRoom.getId())
                .memberNickname(member.getNickname())
                .chatContent(chatContent+i)
                .build()));

        RedisFindChatListServiceRequest findChatRequest = RedisFindChatListServiceRequest.builder()
                .chatId(10L)
                .chatRoomId(chatRoom.getId())
                .build();

        // when
        List<RedisChat> chatList = redisService.findChatList(findChatRequest);

        // then
        assertThat(chatList).hasSize(9);
        assertThat(chatList.get(0).getChatContent()).isEqualTo("채팅 테스트8");
        assertThat(chatList.get(chatList.size()-1).getChatContent()).isEqualTo("채팅 테스트0");
    }
}