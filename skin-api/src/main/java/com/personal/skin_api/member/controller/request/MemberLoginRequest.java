package com.personal.skin_api.member.controller.request;

import com.personal.skin_api.member.service.dto.request.MemberLoginServiceRequest;
import lombok.*;

@Getter
@NoArgsConstructor
public class MemberLoginRequest {
    private String email;
    private String password;

    public MemberLoginServiceRequest toService() {
        return MemberLoginServiceRequest.builder()
                .email(email)
                .password(password)
                .build();
    }
}
