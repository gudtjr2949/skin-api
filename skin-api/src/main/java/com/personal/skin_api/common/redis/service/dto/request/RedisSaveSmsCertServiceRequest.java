package com.personal.skin_api.common.redis.service.dto.request;

import com.personal.skin_api.member.repository.entity.phone.Phone;
import com.personal.skin_api.sms.service.SmsPurpose;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RedisSaveSmsCertServiceRequest {
    private final SmsPurpose purpose;

    private final Phone phone;
    private final String code;

    @Builder
    private RedisSaveSmsCertServiceRequest(SmsPurpose purpose, String phone, String code) {
        this.purpose = purpose;
        this.phone = new Phone(phone);
        this.code = code;
    }

    public String getPhone() {
        return phone.getPhone();
    }
}
