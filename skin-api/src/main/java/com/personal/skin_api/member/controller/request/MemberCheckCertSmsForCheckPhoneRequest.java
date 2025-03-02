package com.personal.skin_api.member.controller.request;

import com.personal.skin_api.member.service.dto.request.MemberCheckCertMailForCheckMailServiceRequest;
import com.personal.skin_api.member.service.dto.request.MemberCheckCertSmsForCheckPhoneServiceRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberCheckCertSmsForCheckPhoneRequest {

    private String phone;
    private String code;

    public MemberCheckCertSmsForCheckPhoneServiceRequest toService() {
        return MemberCheckCertSmsForCheckPhoneServiceRequest.builder()
                .phone(phone)
                .code(code)
                .build();
    }
}
