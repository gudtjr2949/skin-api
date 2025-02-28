package com.personal.skin_api.mail.service;


import com.personal.skin_api.mail.service.dto.request.MailSendCertificationForPasswordServiceRequest;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.service.MemberService;
import com.personal.skin_api.member.service.dto.request.MemberSignUpServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
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

    @Autowired
    private MemberRepository memberRepository;

    @Value("${sample.email}")
    private String email;

    @Value("${sample.member-name}")
    private String memberName;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @Test
    void 회원가입에_입력한_이메일에_인증코드를_전송한다() {
        // given
        memberService.signUp(createSignUpRequest());
        String email = this.email;

        // when & then
        assertThatNoException().isThrownBy(() -> mailService.sendCertificationMailForCheckEmail(email));
    }

    @Test
    void 회원가입에서_입력한_이메일_인증코드를_검증한다() {
        // given

        // when

        // then
    }

    @Test
    void 비밀번호를_찾기_위해_입력된_이메일에_인증코드를_전송한다() {
        // given
        memberService.signUp(createSignUpRequest());
        MailSendCertificationForPasswordServiceRequest sendMailRequest = MailSendCertificationForPasswordServiceRequest.builder()
                .email(email)
                .memberName(memberName)
                .build();

        // when & then
        assertThatNoException().isThrownBy(() -> mailService.sendCertificationMailForFindPassword(sendMailRequest));
    }


    private MemberSignUpServiceRequest createSignUpRequest() {
        return MemberSignUpServiceRequest.builder()
                .email(email)
                .password("asd1234!")
                .memberName(memberName)
                .nickname("길동짱짱")
                .phone("01012345678")
                .build();
    }
}