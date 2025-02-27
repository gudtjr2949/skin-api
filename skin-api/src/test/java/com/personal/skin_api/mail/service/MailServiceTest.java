package com.personal.skin_api.mail.service;


import com.personal.skin_api.mail.service.dto.request.MailCertificationServiceRequest;
import com.personal.skin_api.member.service.MemberService;
import com.personal.skin_api.member.service.dto.request.MemberSignUpServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Autowired
    private MemberService memberService;

    @Value("${sample.email}")
    private String email;

    @Value("${sample.member-name}")
    private String memberName;


    @Test
    void 비밀번호를_찾기_위해_입력된_이메일에_메일을_전송한다() {
        // given
        memberService.signUp(createSignUpNoParameterRequest());

        MailCertificationServiceRequest sendMailRequest = MailCertificationServiceRequest.builder()
                .email(email)
                .memberName(memberName)
                .build();

        // when & then
        assertThatNoException().isThrownBy(() -> mailService.sendCertificationMailForFindPassword(sendMailRequest));
    }

    private MemberSignUpServiceRequest createSignUpNoParameterRequest() {
        return MemberSignUpServiceRequest.builder()
                .email(email)
                .password("asd1234!")
                .memberName(memberName)
                .nickname("길동짱짱")
                .phone("01012345678")
                .build();
    }

}