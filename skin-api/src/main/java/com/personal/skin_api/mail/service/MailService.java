package com.personal.skin_api.mail.service;

import com.personal.skin_api.mail.service.dto.request.MailSendCertServiceRequest;

public interface MailService {
    void sendCertMail(MailSendCertServiceRequest request);
}
