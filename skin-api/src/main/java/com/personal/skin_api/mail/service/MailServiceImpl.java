package com.personal.skin_api.mail.service;

import com.personal.skin_api.common.exception.CommonErrorCode;
import com.personal.skin_api.common.exception.MailErrorCode;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.mail.service.dto.request.MailCertificationServiceRequest;
import com.personal.skin_api.member.service.MemberService;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    private final MemberService memberService;

    @Override
    public long sendCertificationMailForFindPassword(MailCertificationServiceRequest request) {
        memberService.checkEmailForCertification(request.toMemberService());

        try {
            String code = UUID.randomUUID().toString().substring(0, 6);
            sendMail(code, request.getEmail());
        } catch (Exception e) {
            throw new RestApiException(MailErrorCode.MAIL_SERVER_ERROR);
        }

        return 0;
    }

    public void sendMail(String code, String email) throws Exception {
        try {
            MimeMessage mimeMessage = createMessage(code, email);
            javaMailSender.send(mimeMessage);
        } catch (MailException mailException){
            mailException.printStackTrace();
            throw   new IllegalAccessException();
        }
    }

    private MimeMessage createMessage(String code, String email) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject("스킨 판매 플랫폼");
        message.setText("이메일 인증코드 : " + code);
        message.setFrom(email);

        return message;
    }
}
