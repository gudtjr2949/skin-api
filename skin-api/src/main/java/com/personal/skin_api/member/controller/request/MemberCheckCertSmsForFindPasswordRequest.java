package com.personal.skin_api.member.controller.request;

import com.personal.skin_api.member.service.dto.request.MemberFindPasswordServiceRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberCheckCertSmsForFindPasswordRequest {
    private String email;
    private String memberName;
    private String code;

    public MemberFindPasswordServiceRequest toService() {
        return MemberFindPasswordServiceRequest.builder()
                .email(email)
                .memberName(memberName)
                .code(code)
                .build();
    }
}
