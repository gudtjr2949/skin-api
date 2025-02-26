package com.personal.skin_api.member.service;

import com.personal.skin_api.member.service.dto.request.*;
import com.personal.skin_api.member.service.dto.response.*;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    void checkEmailDuplicated(String email);
    void checkNicknameDuplicated(String nickname);
    void checkPhoneDuplicated(String phone);
    void signUp(MemberSignUpServiceRequest request);
    MemberLoginResponse login(MemberLoginServiceRequest request);
    MemberFindEmailResponse findEmail(MemberFindEmailServiceRequest request);
    void findPassword(MemberFindPasswordServiceRequest request);
    void modifyPassword(MemberModifyPasswordServiceRequest request);
    MemberDetailResponse findMemberDetail(MemberFindDetailServiceRequest request);
    void modifyMemberDetail(MemberModifyDetailServiceRequest request);
    void withdraw(MemberWithdrawServiceRequest request);
}
