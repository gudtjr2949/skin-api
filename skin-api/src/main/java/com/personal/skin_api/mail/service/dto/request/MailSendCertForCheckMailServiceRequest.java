package com.personal.skin_api.mail.service.dto.request;

import lombok.Builder;

public class MailSendCertForCheckMailServiceRequest {
    private final String purpose;
    private final String email;
    private final String code;

    @Builder
    private MailSendCertForCheckMailServiceRequest(final String purpose, final String email, final String code) {
        this.purpose = purpose;
        this.email = email;
        this.code = code;
    }
}
