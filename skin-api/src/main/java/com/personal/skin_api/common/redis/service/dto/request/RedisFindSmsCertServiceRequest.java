package com.personal.skin_api.common.redis.service.dto.request;


import com.personal.skin_api.member.repository.entity.phone.Phone;
import com.personal.skin_api.sms.service.SmsPurpose;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RedisFindSmsCertServiceRequest {
    private final SmsPurpose purpose;
    private final Phone phone;

    @Builder
    private RedisFindSmsCertServiceRequest(final SmsPurpose purpose, final String phone) {
        this.purpose = purpose;
        this.phone = new Phone(phone);
    }

    public String getPhone() {
        return phone.getPhone();
    }
}
