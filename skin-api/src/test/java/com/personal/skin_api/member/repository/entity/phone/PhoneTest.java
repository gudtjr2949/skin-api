package com.personal.skin_api.member.repository.entity.phone;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.member.repository.entity.nickname.Nickname;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PhoneTest {

    @Test
    void 전화번호가_NULL일_경우_예외가_발생한다() {
        // given
        String nullPhone = null;

        // when & then
        assertThatThrownBy(() -> new Phone(nullPhone)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 전화번호에_공백이_포함된_경우_예외가_발생한다() {
        // given
        String containsBlankPhone = " 01012345678";

        // when & then
        assertThatThrownBy(() -> new Phone(containsBlankPhone)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 전화번호_기준_길이와_다를_경우_예외가_발생한다() {
        // given
        String differentPhone = "010123456789";

        // when & then
        assertThatThrownBy(() -> new Phone(differentPhone)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 전화번호_식별번호가_010이_아닐_경우_예외가_발생한다() {
        // given
        String startWith011Phone = "01112345678";

        // when & then
        assertThatThrownBy(() -> new Phone(startWith011Phone)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 전화번호에_알파벳이_포함된_경우_예외가_발생한다() {
        // given
        String containsSpecialAlphabetPhone = "0101234567a";

        // when & then
        assertThatThrownBy(() -> new Phone(containsSpecialAlphabetPhone)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 전화번호에_특수문자가_포함된_경우_예외가_발생한다() {
        // given
        String containsSpecialCharacterPhone = "0101234567!";

        // when & then
        assertThatThrownBy(() -> new Phone(containsSpecialCharacterPhone)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 전화번호가_정상적으로_생성된다() {
        // given
        String normalPhone = "01012345678";

        // when
        Phone phone = new Phone(normalPhone);

        // then
        assertThat(phone.getPhone()).isEqualTo(normalPhone);
    }
}