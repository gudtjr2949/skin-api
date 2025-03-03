package com.personal.skin_api.member.controller.request;

import com.personal.skin_api.member.service.dto.request.MemberModifyPasswordServiceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberModifyPasswordRequest {
    private String email;
    private String newPassword;

    public MemberModifyPasswordServiceRequest toService() {
        return MemberModifyPasswordServiceRequest.builder()
                .email(email)
                .newPassword(newPassword)
                .build();
    }
}
