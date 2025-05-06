package com.personal.skin_api.member.service;

import com.personal.skin_api.AbstractIntegrationTest;
import com.personal.skin_api.common.exception.RestApiException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class MemberPasswordEncryptionTest extends AbstractIntegrationTest {

    @Autowired
    private MemberPasswordEncryption memberPasswordEncryption;


    @Test
    void 평문으로_입력한_비밀번호가_암호화된다() {
        // given
        String rawPassword = "password123!";

        // when
        String encodedPassword = memberPasswordEncryption.encodePassword(rawPassword);

        // then
        assertThat(encodedPassword).isNotEqualTo(rawPassword);
    }

    @Test
    void 평문으로_입력한_비밀번호화_암호화된_비밀번호를_비교하고_일치한다면_정상처리된다() {
        // given
        String rawPassword = "password123!";
        String encodedPassword = memberPasswordEncryption.encodePassword(rawPassword);

        // when & then
        assertDoesNotThrow(() ->
                memberPasswordEncryption.comparePassword(rawPassword, encodedPassword)
        );
    }
    
    @Test
    void 평문으로_입력한_비밀번호화_암호화된_비밀번호를_비교하고_다르다면_예외가_발생한다() {
        // given
        String rawPassword = "password123!";
        String otherRawPassword = "otherPassword123!";
        String encodedPassword = memberPasswordEncryption.encodePassword(otherRawPassword);
        
        // when & then
        assertThatThrownBy(() -> memberPasswordEncryption.comparePassword(rawPassword, encodedPassword))
                .isInstanceOf(RestApiException.class);
    }
}