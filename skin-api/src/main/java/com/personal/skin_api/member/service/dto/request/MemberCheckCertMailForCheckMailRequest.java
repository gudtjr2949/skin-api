package com.personal.skin_api.member.service.dto.request;

import com.personal.skin_api.member.repository.entity.email.Email;
import lombok.Builder;
import lombok.Getter;

public class MemberCheckCertMailForCheckMailRequest {
    private final Email email;
    private final String code;

    @Builder
    private MemberCheckCertMailForCheckMailRequest(final String email, final String code) {
        this.email = new Email(email);
        this.code = code;
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getCode() {
        return code;
    }
}
