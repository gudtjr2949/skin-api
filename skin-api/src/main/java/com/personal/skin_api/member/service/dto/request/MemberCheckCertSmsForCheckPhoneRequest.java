package com.personal.skin_api.member.service.dto.request;

import com.personal.skin_api.member.repository.entity.phone.Phone;
import lombok.Builder;

public class MemberCheckCertSmsForCheckPhoneRequest {
    private final Phone phone;
    private final String code;

    @Builder
    private MemberCheckCertSmsForCheckPhoneRequest(final String phone, final String code) {
        this.phone = new Phone(phone);
        this.code = code;
    }

    public String getPhone() {
        return phone.getPhone();
    }

    public String getCode() {
        return code;
    }
}
