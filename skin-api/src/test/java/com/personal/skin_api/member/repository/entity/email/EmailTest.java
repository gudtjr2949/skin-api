package com.personal.skin_api.member.repository.entity.email;

import com.personal.skin_api.common.exception.RestApiException;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class EmailTest {
    
    @Test
    void 이메일이_NULL일_경우_예외가_발생한다() {
        // given
        String nullEmail = null;

        // when & then
        assertThatThrownBy(() -> new Email(nullEmail)).isInstanceOf(RestApiException.class);
    }
    
    @Test
    void 이메일에_공백이_포함될_경우_예외가_발생한다() {
        // given
        String containsBlankEmail = "ab c123@naver.com";

        // when & then
        assertThatThrownBy(() -> new Email(containsBlankEmail)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 이메일_길이가_최소길이보다_작은_경우_예외가_발생한다() {
        // given
        String shortEmail = "a@a.a";

        // when & then
        assertThatThrownBy(() -> new Email(shortEmail)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 이메일_길이가_최대길이보다_큰_경우_예외가_발생한다() {
        // given
        String longEmail = "a@naver.".repeat(EmailLengthStrategy.EMAIL_MAX_LENGTH - 7);

        // when & then
        assertThatThrownBy(() -> new Email(longEmail)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 이메일_포맷이_올바르지_않은_경우_예외가_발생한다() {
        // given
        List<String> invalidEmails = List.of(
                "abc$123@naver.com",
                "user@domain",
                "@nodomain.com",
                "user@.com"
        );

        // when & then
        invalidEmails.stream()
                .forEach(invalidEmail -> assertThatThrownBy(() -> new Email(invalidEmail)).isInstanceOf(RestApiException.class));
    }

}