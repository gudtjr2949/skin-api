package com.personal.skin_api.mail.controller.dto.request;

import com.personal.skin_api.mail.service.dto.request.MailSendCertificationForPasswordServiceRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MailSendCertificationRequest {
    private final String email;
    private final String memberName;

    @Builder
    private MailSendCertificationRequest(final String email, final String memberName) {
        this.email = email;
        this.memberName = memberName;
    }

    public MailSendCertificationForPasswordServiceRequest toService() {
        return MailSendCertificationForPasswordServiceRequest.builder()
                .email(email)
                .memberName(memberName)
                .build();
    }
}
