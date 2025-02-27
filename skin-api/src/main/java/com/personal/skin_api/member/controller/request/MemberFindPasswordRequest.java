package com.personal.skin_api.member.controller.request;

import com.personal.skin_api.member.service.dto.request.MemberFindPasswordServiceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberFindPasswordRequest {
    private String email;
    private String memberName;
    private String authNumber;

    public MemberFindPasswordServiceRequest toService() {
        return MemberFindPasswordServiceRequest.builder()
                .email(email)
                .memberName(memberName)
                .build();
    }
}
