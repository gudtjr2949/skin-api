package com.personal.skin_api.member.service;

import com.personal.skin_api.member.service.dto.request.*;
import com.personal.skin_api.member.service.dto.response.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface MemberService {
    String sendCertMailForCheckEmail(String email);
    String sendCertMailForCheckPhone(String phone);
    void checkCertMailForCheckEmail(MemberCheckCertMailForCheckMailServiceRequest request);
    void checkCertSmsForCheckPhone(MemberCheckCertSmsForCheckPhoneServiceRequest request);
    void checkNicknameDuplicated(String nickname);
    void checkPhoneDuplicated(String phone);
    void signUp(MemberSignUpServiceRequest request);
    MemberLoginResponse login(MemberLoginServiceRequest request);
    MemberReissueTokenResponse reissueToken(String refreshUUID, HttpServletResponse response);
    String sendCertSmsForFindEmail(String phone);
    MemberFindEmailResponse findEmail(MemberFindEmailServiceRequest request);
    String sendCertMailForFindPassword(String email);
    void findPassword(MemberFindPasswordServiceRequest request);
    void modifyPassword(MemberModifyPasswordServiceRequest request);
    MemberDetailResponse findMemberDetail(MemberFindDetailServiceRequest request);
    void modifyMemberDetail(MemberModifyDetailServiceRequest request);
    List<Cookie> logout(String refreshUUID);
    void withdraw(MemberWithdrawServiceRequest request);

}
