package com.personal.skin_api.common.redis.service;

import com.personal.skin_api.common.exception.CommonErrorCode;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.redis.service.dto.request.*;
import com.personal.skin_api.common.redis.service.dto.RedisChat;
import com.personal.skin_api.common.security.JwtTokenConstant;
import com.personal.skin_api.common.util.TimeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static com.personal.skin_api.chat.repository.ChatManageStrategy.CHAT_SIZE;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private static final long MAIL_TTL = 3, SMS_TTL = 3;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveMailCertification(RedisSaveMailCertServiceRequest request) {
        String key = generateKey(request.getPurpose().toString(), request.getEmail());

        try {
            redisTemplate.opsForValue().set(key, request.getCode(), Duration.ofMinutes(MAIL_TTL));
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void saveSmsCertification(RedisSaveSmsCertServiceRequest request) {
        String key = generateKey(request.getPurpose().toString(), request.getPhone());

        try {
            redisTemplate.opsForValue().set(key, request.getCode(), Duration.ofMinutes(SMS_TTL));
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String findMailCertification(RedisFindMailCertServiceRequest request) {
        String key = generateKey(request.getPurpose().toString(), request.getEmail());

        try {
            String findCode = redisTemplate.opsForValue().get(key).toString();
            return findCode;
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String findSmsCertification(RedisFindSmsCertServiceRequest request) {
        String key = generateKey(request.getPurpose().toString(), request.getPhone());

        try {
            String findCode = redisTemplate.opsForValue().get(key).toString();
            return findCode;
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String saveRefreshToken(RedisSaveRefreshTokenServiceRequest request) {
        String refreshUUID = UUID.randomUUID().toString();
        String key = generateKey(request.getPurpose().toString(), refreshUUID);

        try {
            redisTemplate.opsForValue().set(key, request.getRefreshToken(), Duration.ofMillis(JwtTokenConstant.refreshExpirationTime));
            return refreshUUID;
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String findRefreshToken(RedisFindRefreshTokenServiceRequest request) {
        String key = generateKey(request.getPurpose().toString(), request.getRefreshUUID());

        try {
            String findRefreshToken = redisTemplate.opsForValue().get(key).toString();
            return findRefreshToken;
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteRefreshToken(RedisDeleteRefreshTokenServiceRequest request) {
        String key = generateKey(request.getPurpose().toString(), request.getRefreshUUID());

        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public LocalDateTime saveChat(RedisSaveChatServiceRequest request) {
        Long messageId = redisTemplate.opsForValue().increment("chat:message:id:seq"); // 채팅 메시지 고유 ID 생성

        String messageKey = "chat:message:" + messageId; // Redis에 저장할 때 사용할 채팅 메시지 해시 키

        Long now = System.currentTimeMillis();
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("memberNickname", request.getMemberNickname());
        messageMap.put("chatContent", request.getChatContent());
        messageMap.put("createdAt", now);

        redisTemplate.opsForHash().putAll(messageKey, messageMap);
        String roomKey = "chat:room:" + request.getChatRoomId();
        redisTemplate.opsForZSet().add(roomKey, messageId, messageId);

        return TimeConverter.toLocalDateTime(now);
    }

    @Override
    public List<RedisChat> findChatList(RedisFindChatListServiceRequest request) {
        String roomKey = "chat:room:" + request.getChatRoomId();

        Set<ZSetOperations.TypedTuple<Object>> messageIds;

        if (request.getChatId() == null || request.getChatId().equals(null) ||  request.getChatId().equals(0L)) {
            messageIds = redisTemplate.opsForZSet()
                    .reverseRangeWithScores(roomKey, 0, CHAT_SIZE-1);
        } else {
            messageIds = redisTemplate.opsForZSet()
                    .reverseRangeByScoreWithScores(roomKey, 0, request.getChatId() - 1, 0, CHAT_SIZE);
        }

        List<RedisChat> chatList = new ArrayList<>();
        for (ZSetOperations.TypedTuple<Object> tuple : messageIds) {
            String messageKey = "chat:message:" + tuple.getValue();
            Map<Object, Object> entries = redisTemplate.opsForHash().entries(messageKey);

            if (!entries.isEmpty()) {
                Long messageId = Long.parseLong(tuple.getValue().toString());
                RedisChat chat = RedisChat.builder()
                        .id(messageId)
                        .chatContent((String) entries.get("chatContent"))
                        .memberNickname((String) entries.get("memberNickname"))
                        .createdAt(TimeConverter.toLocalDateTime(
                                Long.parseLong(entries.get("createdAt").toString())))
                        .build();
                chatList.add(chat);
            }
        }

        return chatList;
    }


    /**
     * Redis에 저장할 키를 생성한다
     * @param firstKey 첫 번째 키 (목적을 나타냄)
     * @param secondKey 두 번째 키 (사용자 판별용 문자열을 나타냄)
     * @return Redis에 저장할 키
     */
    private static String generateKey(String firstKey, String secondKey) {
        return String.join(":", firstKey, secondKey);
    }
}
