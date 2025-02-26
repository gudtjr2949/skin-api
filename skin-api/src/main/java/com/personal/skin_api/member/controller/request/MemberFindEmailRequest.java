package com.personal.skin_api.member.controller.request;

import com.personal.skin_api.member.service.dto.request.MemberFindEmailServiceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberFindEmailRequest {
    private String memberName;
    private String phone;
    private String authNumber;

    public MemberFindEmailServiceRequest toService() {
        return MemberFindEmailServiceRequest.builder()
                .memberName(memberName)
                .phone(phone)
                .authNumber(authNumber)
                .build();
    }
}
