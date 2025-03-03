package com.personal.skin_api.sms.service;

import com.personal.skin_api.common.exception.CommonErrorCode;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.redis.service.RedisService;
import com.personal.skin_api.common.redis.service.dto.request.RedisSaveMailCertServiceRequest;
import com.personal.skin_api.common.util.CertCodeGenerator;
import com.personal.skin_api.member.repository.entity.phone.Phone;
import com.personal.skin_api.sms.service.dto.SmsSendCertServiceRequest;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class SmsService {

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecret;

    @Value("${coolsms.from.phone}")
    private String fromPhone;
    private final RedisService redisService;
    private final Message coolsms = new Message(apiKey, apiSecret);

    public String sendSMS(SmsSendCertServiceRequest request) {
        // 발신 정보 설정
        HashMap<String, String> params = makeParams(request.getPhone(), request.getCode());

        try {
            coolsms.send(params);
        } catch (CoolsmsException e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }

        redisService.saveMailCertification(RedisSaveMailCertServiceRequest.builder().build());

        return "메시지 전송완료";
    }

    private HashMap<String, String> makeParams(String toPhone, String code) {
        HashMap<String, String> params = new HashMap<>();
        params.put("from", fromPhone);
        params.put("type", "SMS");
        params.put("app_version", "test app 1.2");
        params.put("to", toPhone);
        params.put("text", code);
        return params;
    }
}
