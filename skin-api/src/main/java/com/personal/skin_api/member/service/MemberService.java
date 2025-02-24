package com.personal.skin_api.member.service;

import com.personal.skin_api.member.service.dto.request.MemberLoginServiceRequest;
import com.personal.skin_api.member.service.dto.request.MemberSignUpServiceRequest;
import com.personal.skin_api.member.service.dto.response.MemberLoginServiceResponse;
import org.springframework.stereotype.Service;

@Service
interface MemberService {
    void checkEmailDuplicated(String email);
    void checkNicknameDuplicated(String nickname);
    void checkPhoneDuplicated(String phone);
    void signUp(MemberSignUpServiceRequest request);
    MemberLoginServiceResponse login(MemberLoginServiceRequest request);
}
