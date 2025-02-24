package com.personal.skin_api.member.repository.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class MemberTest {

    @Test
    void 일반_사용자를_생성한다() {
        // given
        String email = "asd123@naver.com";
        String password = "asd1234!";
        String memberName = "홍길동";
        String nickname = "길동짱짱";
        String phone = "01012345678";

        // when
        Member member = Member.signUpGeneralMember(email, password, memberName, nickname, phone);

        // then
        assertThat(member.getMemberName()).isEqualTo(memberName);
    }
}