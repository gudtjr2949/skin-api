package com.personal.skin_api.common.redis.service;

import com.personal.skin_api.common.redis.service.dto.request.RedisFindMailCertServiceRequest;
import com.personal.skin_api.common.redis.service.dto.request.RedisSaveMailCertServiceRequest;
import com.personal.skin_api.common.util.CertCodeGenerator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Autowired
    private CertCodeGenerator codeGenerator;

    @Test
    void 메일_인증코드를_Redis에_저장하고_조회한다() {
        // given
        String code = codeGenerator.createCertCodeAtMail();
        String email = "asd123@naver.com";
        RedisSaveMailCertServiceRequest saveMailRequest = RedisSaveMailCertServiceRequest.builder()
                .purpose("purpose")
                .email(email)
                .code(code)
                .build();

        RedisFindMailCertServiceRequest findMailRequest = RedisFindMailCertServiceRequest.builder()
                .purpose("purpose")
                .email(email)
                .build();

        // when
        redisService.saveMailCertification(saveMailRequest);
        String findCertificationCode = redisService.findMailCertification(findMailRequest);

        // then
        assertThat(findCertificationCode).isEqualTo(code);
    }
}