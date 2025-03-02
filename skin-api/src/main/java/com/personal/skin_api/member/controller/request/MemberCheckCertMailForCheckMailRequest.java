package com.personal.skin_api.member.controller.request;

import com.personal.skin_api.member.service.dto.request.MemberCheckCertMailForCheckMailServiceRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberCheckCertMailForCheckMailRequest {

    private String email;
    private String code;

    public MemberCheckCertMailForCheckMailServiceRequest toService() {
        return MemberCheckCertMailForCheckMailServiceRequest.builder()
                .email(email)
                .code(code)
                .build();
    }
}
