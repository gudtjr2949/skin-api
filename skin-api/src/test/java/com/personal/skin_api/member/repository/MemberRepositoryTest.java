package com.personal.skin_api.member.repository;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.email.Email;
import com.personal.skin_api.member.repository.entity.member_name.MemberName;
import com.personal.skin_api.member.repository.entity.password.Password;

import com.personal.skin_api.member.repository.entity.phone.Phone;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 사용자를_생성하고_조회한다() {
        // give
        Member member = createMember();

        // when
        memberRepository.save(member);
        Optional<Member> findMember = memberRepository.findById(member.getId());

        // then
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getId()).isEqualTo(member.getId());
    }

    @Test
    void 입력된_이메일_패스워드가_데이터베이스에_존재한다면_로그인에_성공한다() {
        // given
        Member member = createMember();
        memberRepository.save(member);

        // when
        Optional<Member> loginMember = memberRepository.findMemberByEmailAndPassword(new Email(member.getEmail()),
                new Password(member.getPassword()));

        // then
        assertThat(loginMember).isPresent();
        assertThat(loginMember.get().getId()).isEqualTo(member.getId());
    }

    @Test
    void 이름과_전화번호를_사용해_회원정보를_조회한다() {
        // given
        Member member = createMember();
        memberRepository.save(member);

        // when
        Optional<Member> findMember = memberRepository.findMemberByMemberNameAndPhone(new MemberName(member.getMemberName()),
                new Phone(member.getPhone()));

        // then
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getId()).isEqualTo(member.getId());
    }

    @Test
    void 이메일과_전화번호를_사용해_회원정보를_조회한다() {
        // given
        Member member = createMember();
        memberRepository.save(member);

        // when
        Optional<Member> findMember = memberRepository.findMemberByEmailAndPhone(new Email(member.getEmail()), new Phone(member.getPhone()));

        // then
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getId()).isEqualTo(member.getId());
    }
    
    @Test
    void 비밀번호를_재설정한다() {
        // given
        
        // when
        
        // then
    }

    private Member createMember() {
        String email = "asd123@naver.com";
        String password = "asd1234!";
        String memberName = "홍길동";
        String nickname = "길동짱짱";
        String phone = "01012345678";

        return Member.signUpGeneralMember(email, password, memberName, nickname, phone);
    }
}