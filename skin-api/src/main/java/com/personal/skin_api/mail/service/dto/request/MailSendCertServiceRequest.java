package com.personal.skin_api.mail.service.dto.request;

import com.personal.skin_api.mail.service.MailPurpose;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MailSendCertServiceRequest {
    private final MailPurpose purpose;
    private final String email;
    private final String code;

    @Builder
    private MailSendCertServiceRequest(final MailPurpose purpose, final String email, final String code) {
        this.purpose = purpose;
        this.email = email;
        this.code = code;
    }
}
