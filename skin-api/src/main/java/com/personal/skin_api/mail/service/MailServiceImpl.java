package com.personal.skin_api.mail.service;

import com.personal.skin_api.common.exception.MailErrorCode;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.mail.service.dto.request.MailSendCertServiceRequest;
import com.personal.skin_api.member.service.MemberService;
import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final String MAIL_MAIN_SUBJECT = "스킨 거래 플랫폼";
    private final String MAIL_MESSAGE = "인증 코드 : ";

    private final JavaMailSender javaMailSender;

    @Override
    public void sendCertMail(MailSendCertServiceRequest request) {
        try {
            MimeMessage message = createMessage(request);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RestApiException(MailErrorCode.MAIL_SERVER_ERROR);
        }
    }

    public MimeMessage createMessage(MailSendCertServiceRequest request) throws Exception {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            message.addRecipients(Message.RecipientType.TO, request.getEmail());
            message.setSubject(MAIL_MAIN_SUBJECT + " - " + request.getPurpose());
            message.setText(MAIL_MESSAGE + request.getCode());
            message.setFrom(request.getEmail());

            return message;
        } catch (MailException mailException){
            mailException.printStackTrace();
            throw new IllegalAccessException();
        }
    }
}
