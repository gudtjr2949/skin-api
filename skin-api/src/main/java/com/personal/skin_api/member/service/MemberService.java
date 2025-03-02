package com.personal.skin_api.member.service;

import com.personal.skin_api.member.service.dto.request.*;
import com.personal.skin_api.member.service.dto.response.*;

public interface MemberService {
    String sendCertMailForCheckEmail(String email);
    String sendCertMailForCheckPhone(String phone);
    void checkCertMailForCheckEmail(MemberCheckCertMailForCheckMailRequest request);
    void checkCertSmsForCheckPhone(MemberCheckCertSmsForCheckPhoneRequest request);
    void checkNicknameDuplicated(String nickname);
    void checkPhoneDuplicated(String phone);
    void signUp(MemberSignUpServiceRequest request);
    MemberLoginResponse login(MemberLoginServiceRequest request);
    String sendCertSmsForFindEmail(String phone);
    MemberFindEmailResponse findEmail(MemberFindEmailServiceRequest request);
    String sendCertMailForFindPassword(String email);
    void findPassword(MemberFindPasswordServiceRequest request);
    void modifyPassword(MemberModifyPasswordServiceRequest request);
    MemberDetailResponse findMemberDetail(MemberFindDetailServiceRequest request);
    void modifyMemberDetail(MemberModifyDetailServiceRequest request);
    void withdraw(MemberWithdrawServiceRequest request);
}
