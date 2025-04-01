package com.personal.skin_api.member.service;

import com.personal.skin_api.common.exception.RestApiException;

import com.personal.skin_api.common.redis.TokenPurpose;
import com.personal.skin_api.common.redis.service.RedisService;
import com.personal.skin_api.common.redis.service.dto.request.*;
import com.personal.skin_api.common.security.JwtTokenConstant;
import com.personal.skin_api.common.security.JwtTokenProvider;
import com.personal.skin_api.common.util.CertCodeGenerator;
import com.personal.skin_api.common.redis.MailPurpose;
import com.personal.skin_api.mail.service.MailService;
import com.personal.skin_api.mail.service.dto.request.MailSendCertServiceRequest;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.*;
import com.personal.skin_api.member.repository.entity.email.Email;
import com.personal.skin_api.member.repository.entity.member_name.MemberName;
import com.personal.skin_api.member.repository.entity.nickname.Nickname;
import com.personal.skin_api.member.repository.entity.password.Password;
import com.personal.skin_api.member.repository.entity.phone.Phone;
import com.personal.skin_api.member.service.dto.request.*;
import com.personal.skin_api.member.service.dto.response.*;
import com.personal.skin_api.sms.service.SmsPurpose;
import com.personal.skin_api.sms.service.SmsService;
import com.personal.skin_api.sms.service.dto.SmsSendCertServiceRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.personal.skin_api.common.exception.member.MemberErrorCode.*;

@Service
@RequiredArgsConstructor
class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MailService mailService;
    private final SmsService smsService;
    private final RedisService redisService;
    private final CertCodeGenerator codeGenerator;
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * 회원가입에 입력된 이메일에 인증코드를 전송한다.
     * @param email 인증코드를 전송할 이메일
     */
    @Override
    public String sendCertMailForCheckEmail(String email) {
        String code = codeGenerator.createCertCodeAtMail();

        mailService.sendCertMail(MailSendCertServiceRequest.builder()
                .purpose(MailPurpose.CHECK_EMAIL)
                .email(email)
                .code(code)
                .build());

        redisService.saveMailCertification(RedisSaveMailCertServiceRequest.builder()
                .purpose(MailPurpose.CHECK_EMAIL)
                .email(email)
                .code(code)
                .build());

        return code;
    }

    @Override
    public String sendCertMailForCheckPhone(String phone) {
        String code = codeGenerator.createCertCodeAtMail();

        smsService.sendSMS(SmsSendCertServiceRequest.builder()
                .phone(phone)
                .code(code)
                .build());

        redisService.saveSmsCertification(RedisSaveSmsCertServiceRequest.builder()
                .purpose(SmsPurpose.CHECK_PHONE)
                .phone(phone)
                .code(code)
                .build());

        return code;
    }

    /**
     * 이메일 검증에 입력한 인증코드를 확인한다.
     * @param request 인증코드 검증에 필요한 정보
     */
    @Override
    public void checkCertMailForCheckEmail(MemberCheckCertMailForCheckMailServiceRequest request) {
        RedisFindMailCertServiceRequest findCodeRequest = RedisFindMailCertServiceRequest.builder()
                .purpose(MailPurpose.CHECK_EMAIL)
                .email(request.getEmail())
                .build();

        String findCode = redisService.findMailCertification(findCodeRequest);

        if (!request.getCode().equals(findCode))
            throw new RestApiException(NOT_FOUND_CERTIFICATION_CODE);
    }

    @Override
    public void checkCertSmsForCheckPhone(MemberCheckCertSmsForCheckPhoneServiceRequest request) {
        RedisFindSmsCertServiceRequest findCodeRequest = RedisFindSmsCertServiceRequest.builder()
                .purpose(SmsPurpose.CHECK_PHONE)
                .phone(request.getPhone())
                .build();

        String findCode = redisService.findSmsCertification(findCodeRequest);

        if (!request.getCode().equals(findCode)) throw new RestApiException(NOT_FOUND_CERTIFICATION_CODE);
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
     * 회원가입 최종 확인을 위해, 이메일, 닉네임, 전화번호 중복확인을 진행한다.
     * @param request 중복 확인에 필요한 이메일, 닉네임, 전화번호
     */
    private void checkDuplicatedMemberInfo(final MemberSignUpServiceRequest request) {
        checkEmailDuplicated(request.getEmail());
        checkNicknameDuplicated(request.getNickname());
        checkPhoneDuplicated(request.getPhone());
    }

    private void checkEmailDuplicated(final String email) {
        if (memberRepository.findMemberByEmail(new Email(email)).isPresent()) throw new RestApiException(DUPLICATE_MEMBER);
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

        String accessToken = jwtTokenProvider.generateJwt(request.getEmail(), JwtTokenConstant.accessExpirationTime);
        String refreshToken = jwtTokenProvider.generateJwt(request.getEmail(), JwtTokenConstant.refreshExpirationTime);


        redisService.saveRefreshToken(RedisSaveRefreshTokenServiceRequest.builder()
                .purpose(TokenPurpose.REFRESH_TOKEN)
                .email(loginMember.getEmail())
                .refreshToken(refreshToken)
                .build());


        return MemberLoginResponse.builder()
                .member(loginMember)
                .accessToken(accessToken)
                .build();
    }

    @Override
    public String reissueToken(String email) {
        Member reissuedMember = memberRepository.findMemberByEmail(new Email(email))
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));

        String refreshToken = redisService.findRefreshToken(RedisFindRefreshTokenServiceRequest.builder()
                .purpose(TokenPurpose.REFRESH_TOKEN)
                .email(email).build());

        if (refreshToken == null) {
            throw new RestApiException(REQUIRED_RE_LOGIN);
        }

        String newAccessToken = jwtTokenProvider.generateJwt(email, JwtTokenConstant.accessExpirationTime);
        String newRefreshToken = jwtTokenProvider.generateJwt(email, JwtTokenConstant.refreshExpirationTime);

        redisService.saveRefreshToken(RedisSaveRefreshTokenServiceRequest.builder()
                .purpose(TokenPurpose.REFRESH_TOKEN)
                .email(email)
                .refreshToken(newRefreshToken)
                .build());

        return newAccessToken;
    }

    @Override
    public String sendCertSmsForFindEmail(String phone) {
        String code = codeGenerator.createCertCodeAtMail();

        smsService.sendSMS(SmsSendCertServiceRequest.builder()
                .phone(phone)
                .code(code)
                .build());

        redisService.saveSmsCertification(RedisSaveSmsCertServiceRequest.builder()
                .purpose(SmsPurpose.FIND_EMAIL)
                .phone(phone)
                .code(code)
                .build());

        return code;
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

        String findCode = redisService.findSmsCertification(RedisFindSmsCertServiceRequest.builder()
                .phone(request.getPhone())
                .purpose(SmsPurpose.FIND_EMAIL)
                .build());

        if (!request.getCode().equals(findCode))
            throw new RestApiException(NOT_FOUND_CERTIFICATION_CODE);

        return MemberFindEmailResponse.builder()
                .email(findmember.getEmail())
                .build();
    }

    /**
     * 비밀번호를 찾기 위해 인증번호를 전송한다.
     * @param email 인증코드를 전송할 이메일
     */
    @Override
    public String sendCertMailForFindPassword(String email) {
        String code = codeGenerator.createCertCodeAtMail();

        // 인증 메일 전송
        mailService.sendCertMail(MailSendCertServiceRequest.builder()
                .purpose(MailPurpose.FIND_PASSWORD)
                .email(email)
                .code(code)
                .build());

        // Redis에 저장
        redisService.saveMailCertification(RedisSaveMailCertServiceRequest.builder()
                .purpose(MailPurpose.FIND_PASSWORD)
                .email(email)
                .code(code)
                .build());

        return code;
    }

    /**
     * 비밀번호를 재설정하기 위해 입력된 이메일과 회원 이름, 인증코드를 검증한다.
     * @param request 비밀번호를 재설정을 진행할 회원 정보 검증을 위해 입력한 이메일, 이름, 인증코드
     */
    @Override
    public void findPassword(MemberFindPasswordServiceRequest request) {
        memberRepository.findMemberByEmailAndMemberName(new Email(request.getEmail()), new MemberName(request.getMemberName()))
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));

        RedisFindMailCertServiceRequest findCodeRequest = RedisFindMailCertServiceRequest.builder()
                .purpose(MailPurpose.FIND_PASSWORD)
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
