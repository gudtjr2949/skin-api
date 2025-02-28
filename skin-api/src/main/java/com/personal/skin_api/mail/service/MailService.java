package com.personal.skin_api.mail.service;

import com.personal.skin_api.mail.service.dto.request.MailSendCertificationForPasswordServiceRequest;

public interface MailService {
    void sendCertificationMailForCheckEmail(String email);
    void checkCertificationCodeForCheckEmail(String code);
    void sendCertificationMailForFindPassword(MailSendCertificationForPasswordServiceRequest request);
}
