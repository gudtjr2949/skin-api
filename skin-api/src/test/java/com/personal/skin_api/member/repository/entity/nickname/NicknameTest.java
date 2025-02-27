package com.personal.skin_api.member.repository.entity.nickname;

import com.personal.skin_api.common.exception.RestApiException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class NicknameTest {

    @Test
    void 닉네임이_NULL일_경우_예외가_발생한다() {
        // given
        String nullNickname = null;

        // when & then
        assertThatThrownBy(() -> new Nickname(nullNickname)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 닉네임에_공백이_포함된_경우_예외가_발생한다() {
        // given
        String containsBlankNickname = " 꿀맛코딱지";

        // when & then
        assertThatThrownBy(() -> new Nickname(containsBlankNickname)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 닉네임_길이가_최소길이보다_작은_경우_예외가_발생한다() {
        // given
        String shortNickname = "s";

        // when & then
        assertThatThrownBy(() -> new Nickname(shortNickname)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 닉네임_길이가_최대길이보다_큰_경우_예외가_발생한다() {
        // given
        String longNickname = "abcdefghi";

        // when & then
        assertThatThrownBy(() -> new Nickname(longNickname)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 닉네임에_특수문자가_포함된_경우_예외가_발생한다() {
        // given
        String containsSpecialCharacterNickname = "꿀맛코딱지!";

        // when & then
        assertThatThrownBy(() -> new Nickname(containsSpecialCharacterNickname)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 닉네임이_정상적으로_생성된다() {
        // given
        String normalNickname = "꿀맛코딱지";

        // when
        Nickname nickname = new Nickname(normalNickname);

        // then
        assertThat(nickname.getNickname()).isEqualTo(normalNickname);
    }
}