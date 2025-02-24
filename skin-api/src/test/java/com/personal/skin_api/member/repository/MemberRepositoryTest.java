package com.personal.skin_api.member.repository;

import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.member.repository.entity.email.Email;
import com.personal.skin_api.member.repository.entity.member_name.MemberName;
import com.personal.skin_api.member.repository.entity.nickname.Nickname;
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
    void 이메일을_통해_사용자_정보를_조회한다() {
        // given
        Member member = createMember();
        memberRepository.save(member);

        // when
        Optional<Member> findMember = memberRepository.findMemberByEmail(new Email(member.getEmail()));

        // then
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getEmail()).isEqualTo(member.getEmail());
        assertThat(findMember.get().getMemberName()).isEqualTo(member.getMemberName());
    }
    
    @Test
    void 닉네임을_통해_사용자_정보를_조회한다() {
        // given
        Member member = createMember();
        memberRepository.save(member);

        // when
        Optional<Member> findMember = memberRepository.findMemberByNickname(new Nickname(member.getNickname()));
        
        // then
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getEmail()).isEqualTo(member.getEmail());
        assertThat(findMember.get().getMemberName()).isEqualTo(member.getMemberName());
    }

    @Test
    void 전화번호를_통해_사용자_정보를_조회한다() {
        // given
        Member member = createMember();
        memberRepository.save(member);

        // when
        Optional<Member> findMember = memberRepository.findMemberByPhone(new Phone(member.getPhone()));

        // then
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getEmail()).isEqualTo(member.getEmail());
        assertThat(findMember.get().getMemberName()).isEqualTo(member.getMemberName());
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
        Member member = createMember();
        memberRepository.save(member);
        String newPassword = "asd5678!";

        // when
        member.modifyPassword(newPassword);
        Optional<Member> findMember = memberRepository.findById(member.getId());

        // then
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getPassword()).isEqualTo(newPassword);
    }
    
    @Test
    void 회원탈퇴를_할_경우_회원상태가_WITHDRAW로_변경된다() {
        // given
        Member member = createMember();
        memberRepository.save(member);
        
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
}