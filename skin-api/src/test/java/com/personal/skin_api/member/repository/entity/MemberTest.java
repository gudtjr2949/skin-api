package com.personal.skin_api.member.repository.entity;

import com.personal.skin_api.member.repository.entity.password.Password;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class MemberTest {

    @Test
    void 회원탈퇴를_할_경우_회원상태가_WITHDRAW로_변경된다() {
        // given
        Member member = createMember();

        // when
        member.withdraw();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.WITHDRAWN);
    }

    private Member createMember() {
        String email = "asd123@naver.com";
        String password = "asd1234!";
        String memberName = "홍길동";
        String nickname = "길동짱짱";
        String phone = "01012345678";
        MemberStatus status = MemberStatus.ACTIVE;
        MemberRole role = MemberRole.GENERAL;

        return Member.builder()
                .email(email)
                .password(password)
                .memberName(memberName)
                .nickname(nickname)
                .phone(phone)
                .status(status)
                .role(role)
                .build();
    }

    @Test
    void OAuth_회원의_비밀번호는_기본_비밀번호로_설정된다() {
        // given & when
        String oauthPassword = Password.fromOAuth();

        // then
        assertThat(oauthPassword).isEqualTo(Password.fromOAuth());
    }

    @Test
    void 서비스명에_맞는_AuthProvider가_생성된다() {
        // given
        String provider = "NAVER";
        AuthProvider authProvider = AuthProvider.toAuthProvider(provider);

        // when & then
        assertThat(authProvider).isEqualTo(AuthProvider.NAVER);
    }

    @Test
    void OAuth로_회원가입을_시도한다() {
        // given
        String email = "test123@naver.com";
        String memberName = "홍길동";
        String nickname = "길동짱짱";
        String phone = "01012345678";
        String provider = "NAVER";

        // when
        Member member = Member.fromOAuth(email, memberName, nickname, phone, provider);

        // then
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getPassword()).isEqualTo(Password.fromOAuth());
        assertThat(member.getPassword()).isEqualTo(Password.fromOAuth());
        assertThat(member.getAuthProvider()).isEqualTo(AuthProvider.NAVER);
    }
}