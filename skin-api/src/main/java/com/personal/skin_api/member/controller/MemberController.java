package com.personal.skin_api.member.controller;

import com.personal.skin_api.common.security.JwtTokenConstant;
import com.personal.skin_api.member.controller.request.*;
import com.personal.skin_api.member.service.MemberService;
import com.personal.skin_api.member.service.dto.request.MemberFindDetailServiceRequest;
import com.personal.skin_api.member.service.dto.request.MemberWithdrawServiceRequest;
import com.personal.skin_api.member.service.dto.response.MemberDetailResponse;
import com.personal.skin_api.member.service.dto.response.MemberFindEmailResponse;
import com.personal.skin_api.member.service.dto.response.MemberLoginResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/request-cert-code-signup-email")
    public ResponseEntity<Object> requestCertCodeForSignUpEmail(@RequestParam("email") String email) {
        memberService.sendCertMailForCheckEmail(email);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/check-cert-code-signup-email")
    public ResponseEntity<Object> checkCertCodeForSignUpEmail(@RequestBody MemberCheckCertMailForCheckMailRequest request) {
        memberService.checkCertMailForCheckEmail(request.toService());
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/request-cert-code-signup-phone")
    public ResponseEntity<Object> requestCertCodeForSignUpPhone(@RequestParam("phone") String phone) {
        memberService.sendCertMailForCheckPhone(phone);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/check-cert-code-signup-phone")
    public ResponseEntity<Object> checkCertCodeForSignUpPhone(@RequestBody MemberCheckCertSmsForCheckPhoneRequest request) {
        memberService.checkCertSmsForCheckPhone(request.toService());
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody MemberSignUpRequest request) {
        memberService.signUp(request.toService());
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponse> login(@RequestBody MemberLoginRequest request,
                                                     HttpServletResponse response) {
        MemberLoginResponse loginResponse = memberService.login(request.toService());

        // accessToken 헤더에 담기
        Cookie accessTokenCookie = new Cookie("accessToken", loginResponse.getAccessToken());

        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(1800);

        // 쿠키를 응답에 추가
        response.addCookie(accessTokenCookie);

        // 쿠키 만든 후, 응답 Body에 있는 AccessToken 제거
        loginResponse.removeAccessToken();

        return ResponseEntity.ok().body(loginResponse);
    }

    @GetMapping("/request-cert-code-find-email")
    public ResponseEntity<Object> requestCertCodeForFindEmail(@RequestParam("phone") String phone) {
        memberService.sendCertSmsForFindEmail(phone);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/check-cert-code-find-email")
    public ResponseEntity<MemberFindEmailResponse> checkCertCodeFindEmail(@RequestBody MemberCheckCertMailForFindEmailRequest request) {
        MemberFindEmailResponse response = memberService.findEmail(request.toService());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/request-cert-code-find-password")
    public ResponseEntity<Object> requestCertCodeForFindPassword(@RequestParam("email") String email) {
        memberService.sendCertMailForFindPassword(email);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/check-cert-code-find-password")
    public ResponseEntity<Object> checkCertCodeFindPassword(@RequestBody MemberCheckCertSmsForFindPasswordRequest request) {
        memberService.findPassword(request.toService());
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/modify-password")
    public ResponseEntity<Object> modifyPassword(@RequestBody MemberModifyPasswordRequest request) {
        memberService.modifyPassword(request.toService());
        return ResponseEntity.ok().body(null);
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
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Object> withdraw(@AuthenticationPrincipal UserDetails userDetails) {
        memberService.withdraw(MemberWithdrawServiceRequest.builder()
                .email(userDetails.getUsername())
                .build());

        return ResponseEntity.ok().body(null);
    }
}
