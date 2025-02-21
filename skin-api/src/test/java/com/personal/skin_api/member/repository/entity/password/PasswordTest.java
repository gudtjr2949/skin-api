package com.personal.skin_api.member.repository.entity.password;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.member.repository.entity.password.Password;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
class PasswordTest {

    @Test
    void 비밀번호가_NULL인_경우_예외가_발생한다() {
        // given
        String nullPassword = null;

        // when & then
        assertThatThrownBy(() -> new Password(nullPassword)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호에_공백이_포함된_경우_예외가_발생한다() {
        // given
        String containsBlankPassword = " abcdefg "; // 공백 포함 8자

        // when & then
        assertThatThrownBy(() -> new Password(containsBlankPassword)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호의_길이가_최소길이보다_작은_경우_예외가_발생한다() {
        // given
        String shortPassword = "";

        // when & then
        assertThatThrownBy(() -> new Password(shortPassword)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호의_길이가_최대길이보다_큰_경우_예외가_발생한다() {
        // given
        String longPassword = "abcdefghijklmnopqrxtu";

        // when & then
        assertThatThrownBy(() -> new Password(longPassword)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호에_알파벳이_사용되지_않은_경우_예외가_발생한다() {
        // given
        String noAlphabetPassword = "1234567!";

        // when & then
        assertThatThrownBy(() -> new Password(noAlphabetPassword)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호에_숫자가_사용되지_않은_경우_예외가_발생한다() {
        // given
        String noNumberPassword = "abcdefgh!";

        // when & then
        assertThatThrownBy(() -> new Password(noNumberPassword)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호에_특수문자가_사용되지_않은_경우_예외가_발생한다() {
        // given
        String noSpecialCharacterPassword = "asd12345";

        // when & then
        assertThatThrownBy(() -> new Password(noSpecialCharacterPassword)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호가_정상적으로_생성된다() {
        // given
        String normalPassword = "asd1234!";

        // when
        Password password = new Password(normalPassword);

        // then
        assertThat(password.getPassword()).isEqualTo(normalPassword);
    }

}