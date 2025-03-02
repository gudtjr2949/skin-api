package com.personal.skin_api.member.controller.request;

import com.personal.skin_api.member.service.dto.request.MemberFindEmailServiceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCheckCertMailForFindEmailRequest {
    private String memberName;
    private String phone;
    private String code;

    public MemberFindEmailServiceRequest toService() {
        return MemberFindEmailServiceRequest.builder()
                .memberName(memberName)
                .phone(phone)
                .code(code)
                .build();
    }
}
