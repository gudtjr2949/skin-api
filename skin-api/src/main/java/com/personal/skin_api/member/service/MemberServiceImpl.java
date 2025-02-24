package com.personal.skin_api.member.service;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.MemberErrorCode;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.email.Email;
import com.personal.skin_api.member.repository.entity.nickname.Nickname;
import com.personal.skin_api.member.repository.entity.phone.Phone;
import com.personal.skin_api.member.service.dto.request.MemberLoginServiceRequest;
import com.personal.skin_api.member.service.dto.request.MemberSignUpServiceRequest;
import com.personal.skin_api.member.service.dto.response.MemberLoginServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void checkEmailDuplicated(String email) {
        if (memberRepository.findMemberByEmail(new Email(email)).isPresent()) throw new RestApiException(MemberErrorCode.DUPLICATE_MEMBER);
    }

    @Override
    public void checkNicknameDuplicated(String nickname) {
        if (memberRepository.findMemberByNickname(new Nickname(nickname)).isPresent()) throw new RestApiException(MemberErrorCode.DUPLICATE_MEMBER);
    }

    @Override
    public void checkPhoneDuplicated(String phone) {
        if (memberRepository.findMemberByPhone(new Phone(phone)).isPresent()) throw new RestApiException(MemberErrorCode.DUPLICATE_MEMBER);
    }

    @Override
    public void signUp(final MemberSignUpServiceRequest request) {
        checkDuplicatedMemberInfo(request);
        Member signUpMember = Member.signUpGeneralMember(request);
        memberRepository.save(signUpMember);
    }

    private void checkDuplicatedMemberInfo(final MemberSignUpServiceRequest request) {
        checkEmailDuplicated(request.getEmail());
        checkNicknameDuplicated(request.getNickname());
        checkPhoneDuplicated(request.getPhone());
    }

    @Override
    public MemberLoginServiceResponse login(MemberLoginServiceRequest request) {
        return null;
    }
}
