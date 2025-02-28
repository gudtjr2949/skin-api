package com.personal.skin_api.member.service;

import com.personal.skin_api.common.exception.RestApiException;

import com.personal.skin_api.common.redis.service.RedisService;
import com.personal.skin_api.common.redis.service.dto.request.RedisFindMailCertServiceRequest;
import com.personal.skin_api.common.redis.service.dto.request.RedisSaveMailCertServiceRequest;
import com.personal.skin_api.common.util.CertCodeGenerator;
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

import static com.personal.skin_api.common.exception.member.MemberErrorCode.*;

@Service
@RequiredArgsConstructor
class MemberServiceImpl implements MemberService {
    private static final String FIND_PASSWORD_PURPOSE = "find-password";
    private static final String CHECK_EMAIL_PURPOSE = "check-email";
    private final MemberRepository memberRepository;
    private final RedisService redisService;
    private final CertCodeGenerator codeGenerator;


    /**
     * 회원가입에 입력된 이메일 중복 여부를 확인한다.
     * @param email 중복 여부를 확인할 이메일
     */
    @Override
    public void checkEmailDuplicated(String email) {
        if (memberRepository.findMemberByEmail(new Email(email)).isPresent()) throw new RestApiException(DUPLICATE_MEMBER);
    }

    /**
     * 회원가입에 입력된 닉네임 중복 여부를 확인한다.
     * @param nickname 중복 여부를 확인할 닉네임
     */
    @Override
    public void checkNicknameDuplicated(String nickname) {
        if (memberRepository.findMemberByNickname(new Nickname(nickname)).isPresent()) throw new RestApiException(DUPLICATE_MEMBER);
    }

    /**
     * 회원가입에 입력된 전화번호 중복 여부를 확인한다.
     * @param phone 중복 여부를 확인할 전화번호
     */
    @Override
    public void checkPhoneDuplicated(String phone) {
        if (memberRepository.findMemberByPhone(new Phone(phone)).isPresent()) throw new RestApiException(DUPLICATE_MEMBER);
    }

    /**
     * 일반 사용자 회원가입을 진행한다.
     * @param request 일반 사용자 회원가입에 필요한 정보
     */
    @Override
    public void signUp(final MemberSignUpServiceRequest request) {
        checkDuplicatedMemberInfo(request);
        Member signUpMember = request.toEntity();
        memberRepository.save(signUpMember);
    }

    /**
     * 입력된 정보가 회원정보에 존재하는 이메일인지 확인한다.
     * @param request 존재 여부를 확인할 정보
     */
    @Override
    public void checkEmailForCertification(MemberCertificationForFindPasswordServiceRequest request) {
        Member findMember = memberRepository.findMemberByEmailAndMemberName(new Email(request.getEmail()), new MemberName(request.getMemberName()))
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));
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
    public MemberLoginResponse login(MemberLoginServiceRequest request) {
        Member loginMember = memberRepository.findMemberByEmailAndPassword(new Email(request.getEmail()), new Password(request.getPassword()))
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));

        return MemberLoginResponse.builder()
                .member(loginMember)
                .build();
    }


    // TODO : 전화번호 인증 로직 필요
    /**
     * 이메일을 찾기 위해 입력된 회원 이름과 전화번호, 인증코드를 검증한다.
     * @param request 이메일을 찾기 위해 입력한 회원 이름, 전화번호, 인증코드
     * @return 회원가입에 입력했던 이메일
     */
    @Override
    public MemberFindEmailResponse findEmail(MemberFindEmailServiceRequest request) {
        Member findmember = memberRepository.findMemberByMemberNameAndPhone(new MemberName(request.getMemberName()), new Phone(request.getPhone()))
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));

        return MemberFindEmailResponse.builder()
                .email(findmember.getEmail())
                .build();
    }

    @Override
    public void sendCertMailForFindPassword(String email) {
        String code = codeGenerator.createCertCodeAtMail();

        // 인증 메일 전송

        // Redis에 저장
        redisService.saveMailCertification(RedisSaveMailCertServiceRequest.builder()
                .purpose(FIND_PASSWORD_PURPOSE)
                .email(email)
                .code(code)
                .build());
    }

    // TODO : 이메일 인증 로직 필요
    /**
     * 비밀번호를 재설정하기 위해 입력된 이메일과 회원 이름, 인증코드를 검증한다.
     * @param request 비밀번호를 재설정을 진행할 회원 정보 검증을 위해 입력한 이메일, 이름, 인증코드
     */
    @Override
    public void findPassword(MemberFindPasswordServiceRequest request) {
        memberRepository.findMemberByEmailAndMemberName(new Email(request.getEmail()), new MemberName(request.getMemberName()))
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));

        RedisFindMailCertServiceRequest findCodeRequest = RedisFindMailCertServiceRequest.builder()
                .purpose(FIND_PASSWORD_PURPOSE)
                .email(request.getEmail())
                .build();

        String findCode = redisService.findMailCertification(findCodeRequest);

        if (!request.getCode().equals(findCode))
            throw new RestApiException(NOT_FOUND_CERTIFICATION_CODE);
    }

    /**
     * 비밀번호를 재설정한다.
     * @param request 비밀번호를 재설정하기 위해 입력한 이메일과 새로운 비밀번호
     */
    @Override
    @Transactional
    public void modifyPassword(MemberModifyPasswordServiceRequest request) {
        Member findMember = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));

        findMember.modifyPassword(request.getNewPassword());
    }

    /**
     * 회원정보를 상세조회한다.
     * @param request 회원정보 조회에 필요한 이메일
     * @return 회원 상세 정보
     */
    @Override
    public MemberDetailResponse findMemberDetail(MemberFindDetailServiceRequest request) {
        Member findMember = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));

        return MemberDetailResponse.builder()
                .member(findMember)
                .build();
    }

    /**
     * 회원정보를 수정한다.
     * @param request 회원정보 수정에 필요한 수정정보
     */
    @Override
    @Transactional
    public void modifyMemberDetail(MemberModifyDetailServiceRequest request) {
        Member findMember = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));
        findMember.modifyMemberInfo(request);
    }

    /**
     * 회원탈퇴를 진행한다.
     * @param request 회원탈퇴에 필요한 정보
     */
    @Override
    @Transactional
    public void withdraw(MemberWithdrawServiceRequest request) {
        Member findMember = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));

        findMember.withdraw();
    }
}
