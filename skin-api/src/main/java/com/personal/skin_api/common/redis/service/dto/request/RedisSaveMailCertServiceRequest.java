package com.personal.skin_api.common.redis.service.dto.request;

import com.personal.skin_api.mail.service.MailPurpose;
import com.personal.skin_api.member.repository.entity.email.Email;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RedisSaveMailCertServiceRequest {
    private final MailPurpose purpose;
    private final Email email;
    private final String code;

    @Builder
    private RedisSaveMailCertServiceRequest(final MailPurpose purpose, final String email, final String code) {
        this.purpose = purpose;
        this.email = new Email(email);
        this.code = code;
    }

    public String getEmail() {
        return email.getEmail();
    }
}
