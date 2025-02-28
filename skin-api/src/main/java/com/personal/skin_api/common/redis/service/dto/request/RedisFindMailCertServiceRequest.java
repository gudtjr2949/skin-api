package com.personal.skin_api.common.redis.service.dto.request;

import com.personal.skin_api.mail.service.MailPurpose;
import com.personal.skin_api.member.repository.entity.email.Email;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RedisFindMailCertServiceRequest {
    private final MailPurpose purpose;
    private final Email email;

    @Builder
    private RedisFindMailCertServiceRequest(final MailPurpose purpose, final String email) {
        this.purpose = purpose;
        this.email = new Email(email);
    }

    public String getEmail() {
        return email.getEmail();
    }
}
