package com.personal.skin_api.member.repository.entity;

import com.personal.skin_api.common.exception.RestApiException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PasswordTest {

    @Test
    void 비밀번호가_NULL인_경우_예외가_발생한다() {
        // given
        String nullPassword = null;

        // when & then
        assertThatThrownBy(() -> Password.builder().password(nullPassword).build()).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호의_길이가_최소길이보다_작은_경우_예외가_발생한다() {
        // given
        String shortPassword = "";

        // when & then
        assertThatThrownBy(() -> Password.builder().password(shortPassword).build()).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호의_길이가_최대길이보다_큰_경우_예외가_발생한다() {
        // given
        String longPassword = "abcdefghijklmnopqrxtu";

        // when & then
        assertThatThrownBy(() -> Password.builder().password(longPassword).build()).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호에_알파벳이_사용되지_않은_경우_예외가_발생한다() {
        // given
        String noAlphabetPassword = "1234567!";

        // when & then
        assertThatThrownBy(() -> Password.builder().password(noAlphabetPassword).build()).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호에_숫자가_사용되지_않은_경우_예외가_발생한다() {
        // given
        String noNumberPassword = "abcdefgh!";

        // when & then
        assertThatThrownBy(() -> Password.builder().password(noNumberPassword).build()).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호에_특수문자가_사용되지_않은_경우_예외가_발생한다() {
        // given
        String noSpecialCharacterPassword = "asd12345";

        // when & then
        assertThatThrownBy(() -> Password.builder().password(noSpecialCharacterPassword).build()).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호가_정상적으로_생성된다() {
        // given
        String normalPassword = "asd1234!";

        // when
        Password password = Password.builder()
                .password(normalPassword)
                .build();

        // then
        assertThat(password.getPassword()).isEqualTo(normalPassword);
    }

}