package com.personal.skin_api.mail.service;

import com.personal.skin_api.mail.service.dto.request.MailCertificationServiceRequest;
import org.springframework.stereotype.Service;

public interface MailService {
    long sendCertificationMailForFindPassword(MailCertificationServiceRequest request);
}
