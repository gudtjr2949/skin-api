package com.personal.skin_api.common.redis.service;

import com.personal.skin_api.common.redis.service.dto.request.*;
import com.personal.skin_api.common.redis.service.dto.RedisChat;

import java.time.LocalDateTime;
import java.util.List;

public interface RedisService {
    void saveMailCertification(RedisSaveMailCertServiceRequest request);
    void saveSmsCertification(RedisSaveSmsCertServiceRequest request);
    String findMailCertification(RedisFindMailCertServiceRequest request);
    String findSmsCertification(RedisFindSmsCertServiceRequest request);
    String saveRefreshToken(RedisSaveRefreshTokenServiceRequest request);
    String findRefreshToken(RedisFindRefreshTokenServiceRequest request);
    void deleteRefreshToken(RedisDeleteRefreshTokenServiceRequest request);
    LocalDateTime saveChat(RedisSaveChatServiceRequest request);
    List<RedisChat> findChatList(RedisFindChatListServiceRequest request);

}
