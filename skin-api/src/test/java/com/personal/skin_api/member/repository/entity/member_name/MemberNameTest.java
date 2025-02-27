package com.personal.skin_api.member.repository.entity.member_name;

import com.personal.skin_api.common.exception.RestApiException;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;


@ActiveProfiles("test")
class MemberNameTest {

    @Test
    void 사용자_이름이_NULL인_경우_예외가_발생한다() {
        // given
        String nullMemberName = null;

        // when & then
        assertThatThrownBy(() -> new MemberName(nullMemberName)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 사용자_이름에_공백이_포함되면_예외가_발생한다() {
        // given
        String containsBlankMemberName = " a";

        // when & then
        assertThatThrownBy(() -> new MemberName(containsBlankMemberName)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 사용자_이름의_길이가_최소길이보다_작은_경우_예외가_발생한다() {
        // given
        String shortMemberName = "";

        // when & then
        assertThatThrownBy(() -> new MemberName(shortMemberName)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 사용자_이름의_길이가_최대길이보다_큰_경우_예외가_발생한다() {
        // given
        String longMemberName = "abcdefghijklmnopqrxtu";

        // when & then
        assertThatThrownBy(() -> new MemberName(longMemberName)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 사용자_이름에_숫자가_포함된_경우_예외가_발생한다() {
        // given
        String containsNumberMemberName = "홍길동1";

        // when & then
        assertThatThrownBy(() -> new MemberName(containsNumberMemberName)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 사용자_이름에_특수문자가_포함된_경우_예외가_발생한다() {
        // given
        String containsSpecialCharacterMemberName = "홍길동!";

        // when & then
        assertThatThrownBy(() -> new MemberName(containsSpecialCharacterMemberName)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 사용자_이름이_정상적으로_생성된다() {
        // given
        String normalKoreanMemberName1 = "홍길동";
        String normalKoreanMemberName2 = "박온누리";
        String normalKoreanMemberName3 = "이택";
        String normalEnglishMemberName = "Jack";

        // when
        MemberName koreaMemberName1 = new MemberName(normalKoreanMemberName1);
        MemberName koreaMemberName2 = new MemberName(normalKoreanMemberName2);
        MemberName koreaMemberName3 = new MemberName(normalKoreanMemberName3);
        MemberName englishMemberName = new MemberName(normalEnglishMemberName);

        // then
        assertThat(koreaMemberName1.getMemberName()).isEqualTo(normalKoreanMemberName1);
        assertThat(koreaMemberName2.getMemberName()).isEqualTo(normalKoreanMemberName2);
        assertThat(koreaMemberName3.getMemberName()).isEqualTo(normalKoreanMemberName3);
        assertThat(englishMemberName.getMemberName()).isEqualTo(normalEnglishMemberName);

    }
}