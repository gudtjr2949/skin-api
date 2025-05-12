package com.personal.skin_api.member.controller;

import com.personal.skin_api.common.dto.CommonResponse;
import com.personal.skin_api.common.security.JwtFilter;
import com.personal.skin_api.common.security.JwtTokenConstant;
import com.personal.skin_api.member.controller.request.*;
import com.personal.skin_api.member.service.MemberService;
import com.personal.skin_api.member.service.dto.request.MemberFindDetailServiceRequest;
import com.personal.skin_api.member.service.dto.request.MemberWithdrawServiceRequest;
import com.personal.skin_api.member.service.dto.response.MemberDetailResponse;
import com.personal.skin_api.member.service.dto.response.MemberFindEmailResponse;
import com.personal.skin_api.member.service.dto.response.MemberLoginResponse;
import com.personal.skin_api.member.service.dto.response.MemberReissueTokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/request-cert-code-signup-email")
    public ResponseEntity<Object> requestCertCodeForSignUpEmail(@RequestParam("email") String email) {
        memberService.sendCertMailForCheckEmail(email);
        return ResponseEntity.ok().body(new CommonResponse(200, "이메일 인증용 인증코드 요청 성공"));
    }

    @PostMapping("/check-cert-code-signup-email")
    public ResponseEntity<CommonResponse> checkCertCodeForSignUpEmail(@RequestBody MemberCheckCertMailForCheckMailRequest request) {
        memberService.checkCertMailForCheckEmail(request.toService());
        return ResponseEntity.ok().body(new CommonResponse(200, "이메일 인증 성공"));
    }

    @GetMapping("/check-duplicated-nickname")
    public ResponseEntity<CommonResponse> checkDuplicatedNickname(@RequestParam("nickname") String nickname) {
        memberService.checkNicknameDuplicated(nickname);
        return ResponseEntity.ok().body(new CommonResponse(200, "닉네임 중복여부 판별 성공"));
    }

    @GetMapping("/request-cert-code-signup-phone")
    public ResponseEntity<Object> requestCertCodeForSignUpPhone(@RequestParam("phone") String phone) {
        memberService.sendCertMailForCheckPhone(phone);
        return ResponseEntity.ok().body(new CommonResponse(200, "핸드폰 인증용 인증코드 요청 성공"));
    }

    @PostMapping("/check-cert-code-signup-phone")
    public ResponseEntity<Object> checkCertCodeForSignUpPhone(@RequestBody MemberCheckCertSmsForCheckPhoneRequest request) {
        memberService.checkCertSmsForCheckPhone(request.toService());
        return ResponseEntity.ok().body(new CommonResponse(200, "핸드폰 인증 성공"));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody MemberSignUpRequest request) {
        memberService.signUp(request.toService());
        return ResponseEntity.ok().body(new CommonResponse(200, "회원가입 성공"));
    }

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponse> login(@RequestBody MemberLoginRequest request,
                                                     HttpServletResponse response) {
        MemberLoginResponse loginResponse = memberService.login(request.toService());

        // accessToken 헤더에 담기
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", loginResponse.getAccessToken())
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge((int) (JwtTokenConstant.accessExpirationTime / 1000))
                .build();

        ResponseCookie refreshUUIDCookie = ResponseCookie.from("refreshUUID", loginResponse.getRefreshUUID())
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge((int) (JwtTokenConstant.refreshExpirationTime / 1000))
                .build();


        // 쿠키를 응답에 추가
        response.addHeader("Set-Cookie", accessTokenCookie.toString());
        response.addHeader("Set-Cookie", refreshUUIDCookie.toString());

        // 쿠키 만든 후, 응답 Body에 있는 AccessToken, refreshUUID 제거
        loginResponse.removeAccessToken();
        loginResponse.removeRefreshUUID();

        return ResponseEntity.ok().body(loginResponse);
    }

    @GetMapping("/reissue-access-token")
    public ResponseEntity<Object> reissueAccessToken(@CookieValue("refreshUUID") String refreshUUID,
                                                     HttpServletResponse response) {
        log.info("토큰 재발급 실행 = {}", refreshUUID);
        MemberReissueTokenResponse reissueTokenResponse = memberService.reissueToken(refreshUUID, response);

        // accessToken 헤더에 담기
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", reissueTokenResponse.getNewAccessToken())
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(false)
                .maxAge((int) (JwtTokenConstant.accessExpirationTime / 1000))
                .build();

        ResponseCookie refreshUUIDCookie = ResponseCookie.from("refreshUUID", reissueTokenResponse.getNewRefreshUUID())
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(false)
                .maxAge((int) (JwtTokenConstant.refreshExpirationTime / 1000))
                .build();

        // 쿠키를 응답에 추가
        response.addHeader("Set-Cookie", accessTokenCookie.toString());
        response.addHeader("Set-Cookie", refreshUUIDCookie.toString());


        return ResponseEntity.ok().body(new CommonResponse(200, "Access Token 재발급 성공"));
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse> logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("로그아웃 로직 진입");

        String refreshUUID  = Arrays.stream(request.getCookies())
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst().get();

        List<Cookie> cookies = memberService.logout(refreshUUID);

        cookies.forEach(response::addCookie);

        return ResponseEntity.ok().body(new CommonResponse(200, "로그아웃 성공"));
    }

    @GetMapping("/request-cert-code-find-email")
    public ResponseEntity<Object> requestCertCodeForFindEmail(@RequestParam("phone") String phone) {
        memberService.sendCertSmsForFindEmail(phone);
        return ResponseEntity.ok().body(new CommonResponse(200, "이메일 찾기용 인증코드 요청 성공"));
    }

    @PostMapping("/check-cert-code-find-email")
    public ResponseEntity<MemberFindEmailResponse> checkCertCodeFindEmail(@RequestBody MemberCheckCertMailForFindEmailRequest request) {
        MemberFindEmailResponse response = memberService.findEmail(request.toService());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/request-cert-code-find-password")
    public ResponseEntity<Object> requestCertCodeForFindPassword(@RequestParam("email") String email) {
        memberService.sendCertMailForFindPassword(email);
        return ResponseEntity.ok().body(new CommonResponse(200, "비밀번호 찾기용 인증코드 요청 성공"));
    }

    @PostMapping("/check-cert-code-find-password")
    public ResponseEntity<Object> checkCertCodeFindPassword(@RequestBody MemberCheckCertSmsForFindPasswordRequest request) {
        memberService.findPassword(request.toService());
        return ResponseEntity.ok().body(new CommonResponse(200, "비밀번호 찾기용 인증코드 검증 성공"));
    }

    @PostMapping("/modify-password")
    public ResponseEntity<Object> modifyPassword(@RequestBody MemberModifyPasswordRequest request) {
        memberService.modifyPassword(request.toService());
        return ResponseEntity.ok().body(new CommonResponse(200, "비밀번호 수정 성공"));
    }

    @GetMapping("/detail")
    public ResponseEntity<Object> findMemberDetail(@AuthenticationPrincipal UserDetails userDetails) {
        MemberDetailResponse response = memberService.findMemberDetail(MemberFindDetailServiceRequest.builder()
                .email(userDetails.getUsername())
                .build());

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/modify-detail")
    public ResponseEntity<Object> modifyMemberDetail(@RequestBody MemberModifyDetailRequest request,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        memberService.modifyMemberDetail(request.toService(userDetails.getUsername()));
        return ResponseEntity.ok().body(new CommonResponse(200, "회원 정보 수정 성공"));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Object> withdraw(@AuthenticationPrincipal UserDetails userDetails) {
        memberService.withdraw(MemberWithdrawServiceRequest.builder()
                .email(userDetails.getUsername())
                .build());

        return ResponseEntity.ok().body(new CommonResponse(200, "회원 탈퇴 성공"));
    }

    @GetMapping("/my")
    public ResponseEntity<Object> myPage(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(Map.of("email", userDetails.getUsername()));
    }
}
