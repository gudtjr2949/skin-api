package com.personal.skin_api.member.controller.request;

import com.personal.skin_api.member.service.dto.request.MemberSignUpServiceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSignUpRequest {
    private String email;
    private String password;
    private String memberName;
    private String nickname;
    private String phone;

    public MemberSignUpServiceRequest toService() {
        return MemberSignUpServiceRequest.builder()
                .email(email)
                .password(password)
                .memberName(memberName)
                .nickname(nickname)
                .phone(phone)
                .build();
    }
}
