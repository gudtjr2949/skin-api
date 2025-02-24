package com.personal.skin_api.member.service;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.email.Email;
import com.personal.skin_api.member.repository.entity.password.Password;
import com.personal.skin_api.member.service.dto.request.MemberSignUpServiceRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MemberServiceTest {
    
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @Test
    void 회원가입을_성공하면_회원_객체가_생성된다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();

        // when
        memberService.signUp(signUpRequest);
        Optional<Member> findMember = memberRepository.findMemberByEmailAndPassword(new Email(signUpRequest.getEmail()), new Password(signUpRequest.getPassword()));

        // then
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getEmail()).isEqualTo(signUpRequest.getEmail());
        assertThat(findMember.get().getPassword()).isEqualTo(signUpRequest.getPassword());
    }
    
    @Test
    void 이메일_중복_체크_시_이미_가입된_이메일이라면_예외가_발생한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);
        String duplicatedEmail = signUpRequest.getEmail();
        
        // when & then
        assertThatThrownBy(() -> memberService.checkEmailDuplicated(duplicatedEmail)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 닉네임_중복_체크_시_이미_가입된_닉네임이라면_예외가_발생한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);
        String duplicatedNickname = signUpRequest.getNickname();

        // when & then
        assertThatThrownBy(() -> memberService.checkNicknameDuplicated(duplicatedNickname)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 전화번호_중복_체크_시_이미_가입된_전화번호라면_예외가_발생한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);
        String duplicatedPhone = signUpRequest.getPhone();

        // when & then
        assertThatThrownBy(() -> memberService.checkPhoneDuplicated(duplicatedPhone)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 회원가입_정보에_입력한_이메일_전화번호_닉네임중_하나라도_이미_존재하는_경우_예외가_발생한다() {
        // given
        MemberSignUpServiceRequest firstSignUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(firstSignUpRequest);

        MemberSignUpServiceRequest sameEmailSignUpRequest = createSignUpNeedParameterRequest("asd123@naver.com", "asd1234!", "홍길동", "홍길동전", "01011112222");
        MemberSignUpServiceRequest sameNicknameSignUpRequest = createSignUpNeedParameterRequest("zxc321@naver.com", "asd1234!", "홍길동", "길동짱짱", "01011112222");
        MemberSignUpServiceRequest samePhoneSignUpRequest = createSignUpNeedParameterRequest("zxc321@naver.com", "asd1234!", "홍길동", "홍길동전", "01012345678");
        List<MemberSignUpServiceRequest> signUpRequests = List.of(sameEmailSignUpRequest, sameNicknameSignUpRequest, samePhoneSignUpRequest);

        // when & then
        signUpRequests.stream().forEach(request ->
                assertThatThrownBy(() -> memberService.signUp(request)).isInstanceOf(RestApiException.class));
    }

    @Test
    void 로그인_시도_시_입력한_이메일_비밀번호와_일치하는_회원정보가_없는_경우_예외가_발생한다() {
        // given

        // when & then
    }

    private static MemberSignUpServiceRequest createSignUpNeedParameterRequest(String email, String password, String memberName, String nickname, String phone) {
        return MemberSignUpServiceRequest.builder()
                .email(email)
                .password(password)
                .memberName(memberName)
                .nickname(nickname)
                .phone(phone)
                .build();
    }

    private static MemberSignUpServiceRequest createSignUpNoParameterRequest() {
        return MemberSignUpServiceRequest.builder()
                .email("asd123@naver.com")
                .password("asd1234!")
                .memberName("홍길동")
                .nickname("길동짱짱")
                .phone("01012345678")
                .build();
    }
}