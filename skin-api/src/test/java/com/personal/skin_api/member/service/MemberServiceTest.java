package com.personal.skin_api.member.service;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.member.repository.entity.email.Email;
import com.personal.skin_api.member.repository.entity.password.Password;
import com.personal.skin_api.member.service.dto.request.*;
import com.personal.skin_api.member.service.dto.response.MemberDetailResponse;
import com.personal.skin_api.member.service.dto.response.MemberLoginResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${sample.email}")
    private String email;

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
    void 회원가입에_입력한_이메일에_검증용_인증코드를_전송한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);

        // when & then
        assertThatNoException().isThrownBy(() -> memberService.sendCertMailForCheckEmail(signUpRequest.getEmail()));
    }

    @Test
    void 이메일_검증용_인증코드를_검증한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);

        String code = memberService.sendCertMailForCheckEmail(signUpRequest.getEmail());

        MemberCheckCertMailForCheckMailServiceRequest mailCheckMailRequest = MemberCheckCertMailForCheckMailServiceRequest.builder()
                .email(signUpRequest.getEmail())
                .code(code)
                .build();

        // when & then
        assertThatNoException().isThrownBy(() -> memberService.checkCertMailForCheckEmail(mailCheckMailRequest));
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

        MemberSignUpServiceRequest sameEmailSignUpRequest = createSignUpNeedParameterRequest(firstSignUpRequest.getEmail(), "asd1234!", "홍길동", "홍길동전", "01011112222");
        MemberSignUpServiceRequest sameNicknameSignUpRequest = createSignUpNeedParameterRequest("zxc321@naver.com", "asd1234!", "홍길동", firstSignUpRequest.getNickname(), "01011112222");
        MemberSignUpServiceRequest samePhoneSignUpRequest = createSignUpNeedParameterRequest("zxc321@naver.com", "asd1234!", "홍길동", "홍길동전", firstSignUpRequest.getPhone());

        List<MemberSignUpServiceRequest> signUpRequests =
                List.of(sameEmailSignUpRequest, sameNicknameSignUpRequest, samePhoneSignUpRequest);

        // when & then
        signUpRequests.stream().forEach(request ->
                assertThatThrownBy(() -> memberService.signUp(request)).isInstanceOf(RestApiException.class));
    }

    @Test
    void 로그인_시도_시_입력한_이메일_비밀번호와_일치하는_회원정보가_없는_경우_예외가_발생한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);

        String wrongEmail = "zxc456@naver.com";
        MemberLoginServiceRequest wrongEmailRequest = createLoginMember(wrongEmail, signUpRequest.getPassword());

        String wrongPassword = "zxc4567!";
        MemberLoginServiceRequest wrongPasswordRequest = createLoginMember(signUpRequest.getEmail(), wrongPassword);

        List<MemberLoginServiceRequest> loginRequests = List.of(wrongEmailRequest, wrongPasswordRequest);

        // when & then
        loginRequests.stream().forEach(request -> assertThatThrownBy(() -> memberService.login(request))
                .isInstanceOf(RestApiException.class));
    }

    @Test
    void 입력한_이메일과_비밀번호가_존재한다면_회원정보를_반환한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);
        MemberLoginServiceRequest loginRequest = createLoginMember(signUpRequest.getEmail(), signUpRequest.getPassword());

        // when
        MemberLoginResponse loginMember = memberService.login(loginRequest);

        // then
        assertThat(loginMember.getMemberName()).isEqualTo(signUpRequest.getMemberName());
        assertThat(loginMember.getNickname()).isEqualTo(signUpRequest.getNickname());
    }

    // TODO : sms 금액 충전 시 동작 가능
    /*@Test
    void 이메일을_찾기_위해_입력한_이름과_전화번호가_회원정보에_없는_경우_예외가_발생한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);

        String wrongMemberName = "김영희";
        MemberFindEmailServiceRequest findEmailWithWrongMemberNameRequest = createFindEmailRequest(wrongMemberName, signUpRequest.getPhone());

        String wrongPhone = "01098765432";
        MemberFindEmailServiceRequest findEmailWithWrongPhoneRequest = createFindEmailRequest(signUpRequest.getMemberName(), wrongPhone);

        List<MemberFindEmailServiceRequest> findEmailRequests
                = List.of(findEmailWithWrongMemberNameRequest, findEmailWithWrongPhoneRequest);

        // when & then
        findEmailRequests.stream().forEach(request ->
                assertThatThrownBy(() -> memberService.findEmail(request))
                        .isInstanceOf(RestApiException.class));
    }*/

    // TODO : sms 금액 충전 시 동작 가능
 /*   @Test
    void 이메일을_찾기_위해_입력한_이름과_전화번호가_존재하다면_이메일을_반환한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);

        MemberFindEmailServiceRequest findEmailRequest =
                createFindEmailRequest(signUpRequest.getMemberName(), signUpRequest.getPhone());

        // when
        MemberFindEmailResponse findEmail = memberService.findEmail(findEmailRequest);

        // then
        assertThat(findEmail.getEmail()).isEqualTo(signUpRequest.getEmail());
    }*/

    @Test
    void 비밀번호_재설정용_인증코드를_전송한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);

        // when & then
        assertThatNoException().isThrownBy(() -> memberService.sendCertMailForFindPassword(signUpRequest.getEmail()));
    }

    @Test
    void 비밀번호를_찾기_위해_입력한_사용자_이름_이메일중_하나라도_회원정보에_없는_경우_예외가_발생한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);
        String code = memberService.sendCertMailForFindPassword(signUpRequest.getEmail());

        String wrongEmail = "zxc456@naver.com";
        MemberFindPasswordServiceRequest findPasswordWithWrongEmailRequest =
                createFindPasswordRequest(wrongEmail, signUpRequest.getPhone(), code);

        String wrongPhone = "01098765432";
        MemberFindPasswordServiceRequest findPasswordWithWrongPhoneRequest =
                createFindPasswordRequest(signUpRequest.getEmail(), wrongPhone, code);

        List<MemberFindPasswordServiceRequest> findPasswordRequests
                = List.of(findPasswordWithWrongEmailRequest, findPasswordWithWrongPhoneRequest);

        // when & then
        findPasswordRequests.stream().forEach(request ->
                assertThatThrownBy(() -> memberService.findPassword(request))
                .isInstanceOf(RestApiException.class));
    }

    @Test
    void 비밀번호를_찾기_위해_입력한_인증코드가_다른_경우_예외가_발생한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);
        String code = memberService.sendCertMailForFindPassword(signUpRequest.getEmail());

        String wrongCode = code + "!";

        MemberFindPasswordServiceRequest findPasswordWithWrongCodeRequest =
                createFindPasswordRequest(signUpRequest.getEmail(), signUpRequest.getPhone(), wrongCode);

        // when & then
        assertThatThrownBy(() -> memberService.findPassword(findPasswordWithWrongCodeRequest))
                .isInstanceOf(RestApiException.class);
    }


    @Test
    void 비밀번호를_재설정하기_위해_입력한_사용자_이름_이메일_인증코드가_존재한다면_정상처리한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);
        String code = memberService.sendCertMailForFindPassword(signUpRequest.getEmail());

        MemberFindPasswordServiceRequest findPasswordRequest =
                createFindPasswordRequest(signUpRequest.getEmail(), signUpRequest.getMemberName(), code);

        // when & then
        assertThatNoException().isThrownBy(() -> memberService.findPassword(findPasswordRequest));
    }

    @Test
    void 비밀번호를_재설정한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);
        String newPassword = "zxc1234!";

        MemberModifyPasswordServiceRequest modifyPasswordRequest = MemberModifyPasswordServiceRequest.builder()
                .email(signUpRequest.getEmail())
                .newPassword(newPassword)
                .build();

        // when
        memberService.modifyPassword(modifyPasswordRequest);
        Optional<Member> findMember = memberRepository.findMemberByEmail(new Email(signUpRequest.getEmail()));

        // then
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getPassword()).isEqualTo(newPassword);
    }
    
    
    @Test
    void 마이페이지_조회_시_입력된_이메일이_없는_정보인_경우_예외가_발생한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);
        String wrongEmail = "zxc123@naver.com";

        // when & then
        assertThatThrownBy(() -> memberService.findMemberDetail(MemberFindDetailServiceRequest.builder()
                .email(wrongEmail)
                .build())).isInstanceOf(RestApiException.class);
    }
    
    @Test
    void 마이페이지에_사용할_회원정보를_이메일을_통해_조회한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);

        // when
        MemberDetailResponse memberDetail = memberService.findMemberDetail(MemberFindDetailServiceRequest.builder()
                .email(signUpRequest.getEmail())
                .build());

        // then
        assertThat(memberDetail.getEmail()).isEqualTo(signUpRequest.getEmail());
        assertThat(memberDetail.getMemberName()).isEqualTo(signUpRequest.getMemberName());
        assertThat(memberDetail.getNickname()).isEqualTo(signUpRequest.getNickname());
        assertThat(memberDetail.getPhone()).isEqualTo(signUpRequest.getPhone());
    }
    
    @Test
    void 수정_가능한_모든_회원정보를_수정한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);
        String newMemberName = "김영희";
        String newNickname = "홍길동전";
        String newPhone = "01098765432";

        // when
        MemberModifyDetailServiceRequest modifyRequest = createModifyDetailRequest(signUpRequest.getEmail(), newMemberName, newNickname, newPhone);
        memberService.modifyMemberDetail(modifyRequest);

        Optional<Member> findMember = memberRepository.findMemberByEmail(new Email(signUpRequest.getEmail()));

        // then
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getMemberName()).isEqualTo(newMemberName);
        assertThat(findMember.get().getNickname()).isEqualTo(newNickname);
        assertThat(findMember.get().getPhone()).isEqualTo(newPhone);
    }

    @Test
    void 회원이름만_수정한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);
        String newMemberName = "김영희";

        // when
        MemberModifyDetailServiceRequest modifyRequest = createModifyDetailRequest(signUpRequest.getEmail(), newMemberName, signUpRequest.getNickname(), signUpRequest.getPhone());
        memberService.modifyMemberDetail(modifyRequest);
        Optional<Member> findMember = memberRepository.findMemberByEmail(new Email(signUpRequest.getEmail()));

        // then
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getMemberName()).isEqualTo(newMemberName);
        assertThat(findMember.get().getNickname()).isEqualTo(signUpRequest.getNickname());
        assertThat(findMember.get().getPhone()).isEqualTo(signUpRequest.getPhone());
    }

    @Test
    void 닉네임만_수정한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);
        String newNickname = "홍길동전";

        // when
        MemberModifyDetailServiceRequest modifyRequest = createModifyDetailRequest(signUpRequest.getEmail(), signUpRequest.getMemberName(), newNickname, signUpRequest.getPhone());
        memberService.modifyMemberDetail(modifyRequest);
        Optional<Member> findMember = memberRepository.findMemberByEmail(new Email(signUpRequest.getEmail()));

        // then
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getMemberName()).isEqualTo(signUpRequest.getMemberName());
        assertThat(findMember.get().getNickname()).isEqualTo(newNickname);
        assertThat(findMember.get().getPhone()).isEqualTo(signUpRequest.getPhone());
    }

    @Test
    void 전화번호만_수정한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);
        String newPhone = "01098765432";

        // when
        MemberModifyDetailServiceRequest modifyRequest = createModifyDetailRequest(signUpRequest.getEmail(), signUpRequest.getMemberName(), signUpRequest.getNickname(), newPhone);
        memberService.modifyMemberDetail(modifyRequest);
        Optional<Member> findMember = memberRepository.findMemberByEmail(new Email(signUpRequest.getEmail()));

        // then
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getMemberName()).isEqualTo(signUpRequest.getMemberName());
        assertThat(findMember.get().getNickname()).isEqualTo(signUpRequest.getNickname());
        assertThat(findMember.get().getPhone()).isEqualTo(newPhone);
    }


    @Test
    void 회원_탈퇴한다() {
        // given
        MemberSignUpServiceRequest signUpRequest = createSignUpNoParameterRequest();
        memberService.signUp(signUpRequest);
        MemberWithdrawServiceRequest withdrawRequest = MemberWithdrawServiceRequest.builder()
                .email(signUpRequest.getEmail())
                .build();

        // when
        memberService.withdraw(withdrawRequest);
        Optional<Member> findMember = memberRepository.findMemberByEmail(new Email(signUpRequest.getEmail()));

        // then
        assertThat(findMember).isPresent();
        assertThat(findMember.get().getStatus()).isEqualTo(MemberStatus.WITHDRAWN);
    }

    private static MemberModifyDetailServiceRequest createModifyDetailRequest(String email, String memberName, String nickname, String phone) {
        return MemberModifyDetailServiceRequest.builder()
                .email(email)
                .newMemberName(memberName)
                .newNickname(nickname)
                .newPhone(phone)
                .build();
    }

    private static MemberFindPasswordServiceRequest createFindPasswordRequest(String email, String memberName, String code) {
        return MemberFindPasswordServiceRequest.builder()
                .email(email)
                .memberName(memberName)
                .code(code)
                .build();
    }

    private static MemberFindEmailServiceRequest createFindEmailRequest(String memberName, String phone) {
        return MemberFindEmailServiceRequest.builder()
                .memberName(memberName)
                .phone(phone)
                .build();
    }

    private static MemberLoginServiceRequest createLoginMember(String email, String password) {
        return MemberLoginServiceRequest.builder()
                .email(email)
                .password(password)
                .build();
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

    private MemberSignUpServiceRequest createSignUpNoParameterRequest() {
        return MemberSignUpServiceRequest.builder()
                .email(this.email)
                .password("asd1234!")
                .memberName("홍길동")
                .nickname("길동짱짱")
                .phone("01012345678")
                .build();
    }
}