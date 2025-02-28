package com.personal.skin_api.mail.service;

import com.personal.skin_api.common.exception.MailErrorCode;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.mail.service.dto.request.MailSendCertificationForPasswordServiceRequest;
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
    private final String CHECK_EMAIL_SUBJECTS = "이메일 인증 코드";
    private final String FIND_PASSWORD_SUBJECTS = "비밀번호 찾기 인증 코드";
    private final String MAIL_MESSAGE = "인증 코드 : ";

    private final JavaMailSender javaMailSender;
    private final MemberService memberService;

    // TODO : Redis 에 인증코드 저장 필요
    @Override
    public void sendCertificationMailForCheckEmail(String email) {
        try {
            String code = createCertificationCode();
            sendMail(code, email, CHECK_EMAIL_SUBJECTS);
        } catch (Exception e) {
            throw new RestApiException(MailErrorCode.MAIL_SERVER_ERROR);
        }
    }

    @Override
    public void checkCertificationCodeForCheckEmail(String code) {

    }

    // TODO : Redis 에 인증코드 저장 필요
    @Override
    public void sendCertificationMailForFindPassword(MailSendCertificationForPasswordServiceRequest request) {
        memberService.checkEmailForCertification(request.toMemberService());

        try {
            String code = createCertificationCode();
            sendMail(code, request.getEmail(), FIND_PASSWORD_SUBJECTS);
        } catch (Exception e) {
            throw new RestApiException(MailErrorCode.MAIL_SERVER_ERROR);
        }
    }

    private static String createCertificationCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    public void sendMail(String code, String email, String purpose) throws Exception {
        try {
            MimeMessage mimeMessage = createMessage(code, email, purpose);
            javaMailSender.send(mimeMessage);
        } catch (MailException mailException){
            mailException.printStackTrace();
            throw new IllegalAccessException();
        }
    }

    private MimeMessage createMessage(String code, String email, String purpose) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject(MAIL_MAIN_SUBJECT + " - " + purpose);
        message.setText(MAIL_MESSAGE + code);
        message.setFrom(email);

        return message;
    }
}
