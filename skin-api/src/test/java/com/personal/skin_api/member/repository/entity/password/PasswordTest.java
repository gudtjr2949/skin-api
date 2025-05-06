package com.personal.skin_api.member.repository.entity.password;

import com.personal.skin_api.common.exception.RestApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static com.personal.skin_api.member.repository.entity.password.Password.*;
import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
class PasswordTest {

    @Test
    void 비밀번호가_NULL인_경우_예외가_발생한다() {
        // given
        String nullPassword = null;

        // when & then
        assertThatThrownBy(() -> fromRaw(nullPassword)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호에_공백이_포함된_경우_예외가_발생한다() {
        // given
        String containsBlankPassword = " abcdefg "; // 공백 포함 8자

        // when & then
        assertThatThrownBy(() -> fromRaw(containsBlankPassword)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호의_길이가_최소길이보다_작은_경우_예외가_발생한다() {
        // given
        String shortPassword = "";

        // when & then
        assertThatThrownBy(() -> fromRaw(shortPassword)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호의_길이가_최대길이보다_큰_경우_예외가_발생한다() {
        // given
        String longPassword = "abcdefghijklmnopqrxtu";

        // when & then
        assertThatThrownBy(() -> fromRaw(longPassword)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호에_알파벳이_사용되지_않은_경우_예외가_발생한다() {
        // given
        String noAlphabetPassword = "1234567!";

        // when & then
        assertThatThrownBy(() -> fromRaw(noAlphabetPassword)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호에_숫자가_사용되지_않은_경우_예외가_발생한다() {
        // given
        String noNumberPassword = "abcdefgh!";

        // when & then
        assertThatThrownBy(() -> fromRaw(noNumberPassword)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호에_특수문자가_사용되지_않은_경우_예외가_발생한다() {
        // given
        String noSpecialCharacterPassword = "asd12345";

        // when & then
        assertThatThrownBy(() -> fromRaw(noSpecialCharacterPassword)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호가_정상적으로_생성된다() {
        // given
        String normalPassword = "asd1234!";

        // when
        Password password = fromRaw(normalPassword);

        // then
        assertThat(password.getPassword()).isEqualTo(normalPassword);
    }

    @Test
    void 비밀번호를_재설정할_때_이전에_사용했던_비밀번호를_재사용하려는_경우_예외가_발생한다() {
        // given
        String beforePassword = "asd1234!";
        Password password = fromRaw(beforePassword);

        // when & then
        assertThatThrownBy(() -> password.modifyPassword(beforePassword)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호를_수정할_때_이전에_사용한_비밀번호가_아니지만_기존_비밀번호_기준에_부합하지_않는_경우_예외가_발생한다() {
        // given
        String beforePassword = "asd1234!";
        Password password = fromRaw(beforePassword);
        String newAndShortPassword = "asd123";

        // when & then
        assertThatThrownBy(() -> password.modifyPassword(newAndShortPassword)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 비밀번호가_정상적으로_수정된다() {
        // given
        String beforePassword = "asd1234!";
        Password password = fromRaw(beforePassword);

        String newPassword = "asd5678!";

        // when
        Password modifiedPassword = password.modifyPassword(newPassword);

        // then
        assertThat(modifiedPassword.getPassword()).isEqualTo(newPassword);
    }
}