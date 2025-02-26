package com.personal.skin_api.member.service;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.MemberErrorCode;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.*;
import com.personal.skin_api.member.repository.entity.email.Email;
import com.personal.skin_api.member.repository.entity.member_name.MemberName;
import com.personal.skin_api.member.repository.entity.nickname.Nickname;
import com.personal.skin_api.member.repository.entity.password.Password;
import com.personal.skin_api.member.repository.entity.phone.Phone;
import com.personal.skin_api.member.service.dto.request.*;
import com.personal.skin_api.member.service.dto.response.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입에 입력된 이메일 중복 여부를 확인한다.
     * @param email 중복 여부를 확인할 이메일
     */
    @Override
    public void checkEmailDuplicated(String email) {
        if (memberRepository.findMemberByEmail(new Email(email)).isPresent()) throw new RestApiException(MemberErrorCode.DUPLICATE_MEMBER);
    }

    /**
     * 회원가입에 입력된 닉네임 중복 여부를 확인한다.
     * @param nickname 중복 여부를 확인할 닉네임
     */
    @Override
    public void checkNicknameDuplicated(String nickname) {
        if (memberRepository.findMemberByNickname(new Nickname(nickname)).isPresent()) throw new RestApiException(MemberErrorCode.DUPLICATE_MEMBER);
    }

    /**
     * 회원가입에 입력된 전화번호 중복 여부를 확인한다.
     * @param phone 중복 여부를 확인할 전화번호
     */
    @Override
    public void checkPhoneDuplicated(String phone) {
        if (memberRepository.findMemberByPhone(new Phone(phone)).isPresent()) throw new RestApiException(MemberErrorCode.DUPLICATE_MEMBER);
    }

    /**
     * 회원가입을 진행한다.
     * @param request 회원가입에 필요한 정보
     */
    @Override
    public void signUp(final MemberSignUpServiceRequest request) {
        checkDuplicatedMemberInfo(request);
        Member signUpMember = Member.signUpGeneralMember(request);
        memberRepository.save(signUpMember);
    }

    /**
     * 회원가입 최종 확인을 위해, 이메일, 닉네임, 전화번호 중복확인을 진행한다.
     * @param request 중복 확인에 필요한 이메일, 닉네임, 전화번호
     */
    private void checkDuplicatedMemberInfo(final MemberSignUpServiceRequest request) {
        checkEmailDuplicated(request.getEmail());
        checkNicknameDuplicated(request.getNickname());
        checkPhoneDuplicated(request.getPhone());
    }

    /**
     * 로그인을 진행한다.
     * @param request 로그인에 필요한 이메일, 비밀번호
     * @return 회원정보
     */
    @Override
    public MemberLoginServiceResponse login(MemberLoginServiceRequest request) {
        Member loginMember = memberRepository.findMemberByEmailAndPassword(new Email(request.getEmail()), new Password(request.getPassword()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        return MemberLoginServiceResponse.builder()
                .member(loginMember)
                .build();
    }

    /**
     * 이메일을 찾기 위해 입력된 회원 이름과 전화번호를 검증한다.
     * @param request 이메일을 찾기 위해 입력한 회원 이름과 전화번호
     * @return 회원가입에 입력했던 이메일
     */
    @Override
    public MemberFindEmailServiceResponse findEmail(MemberFindEmailServiceRequest request) {
        Member findmember = memberRepository.findMemberByMemberNameAndPhone(new MemberName(request.getMemberName()),
                new Phone(request.getPhone())).orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        return MemberFindEmailServiceResponse.builder()
                .email(findmember.getEmail())
                .build();
    }

    /**
     * 비밀번호를 재설정을 진행할 회원 정보를 검증한다.
     * @param request 비밀번호를 재설정을 진행할 회원 정보 검증을 위해 입력한 이메일과 전화번호
     */
    @Override
    public void findPassword(MemberFindPasswordServiceRequest request) {
        memberRepository.findMemberByEmailAndPhone(new Email(request.getEmail()), new Phone(request.getPhone()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    /**
     * 비밀번호를 재설정한다.
     * @param request 비밀번호를 재설정하기 위해 입력한 이메일과 새로운 비밀번호
     */
    @Override
    @Transactional
    public void modifyPassword(MemberModifyPasswordServiceRequest request) {
        Member findMember = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        findMember.modifyPassword(request.getNewPassword());
    }

    @Override
    public MemberDetailServiceResponse findMemberDetail(MemberDetailServiceRequest request) {
        return null;
    }

    @Override
    public MemberModifyDetailServiceResponse modifyMemberDetail(MemberModifyDetailServiceRequest request) {
        return null;
    }

    @Override
    public void withdraw(MemberWithdrawServiceRequest request) {

    }
}
