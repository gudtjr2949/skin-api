package com.personal.skin_api.mail.controller;

import com.personal.skin_api.mail.controller.dto.request.MailSendCertificationRequest;
import com.personal.skin_api.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mail")
public class MailController {

    private final MailService mailService;


    @GetMapping("/check-email/cert-code")
    public ResponseEntity<Object> sendCertificationMailForCheckEmail() {
        return ResponseEntity.ok().body(null);
    }


    /**
     * '비밀번호 찾기' 에서 인증코드를 메일을 전송한다.
     *
     * @return
     */
    @PostMapping("/find-password/cert-code")
    public ResponseEntity<Object> sendCertificationMailForFindPassword(@RequestBody MailSendCertificationRequest request) {
        mailService.sendCertificationMailForFindPassword(request.toService());
        return ResponseEntity.ok().body(null);
    }
}
