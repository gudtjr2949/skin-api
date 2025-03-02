package com.personal.skin_api.member.controller;

import com.personal.skin_api.member.controller.request.*;
import com.personal.skin_api.member.service.MemberService;
import com.personal.skin_api.member.service.dto.response.MemberFindEmailResponse;
import com.personal.skin_api.member.service.dto.response.MemberLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/check-cert-code-signup-email")
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
    public ResponseEntity<MemberLoginResponse> login(@RequestBody MemberLoginRequest request) {
        MemberLoginResponse response = memberService.login(request.toService());
        return ResponseEntity.ok().body(response);
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
    
    // TODO : Security Context 적용 후 구현
    @GetMapping("/detail")
    public ResponseEntity<Object> findMemberDetail() {

        return ResponseEntity.ok().body(null);
    }

    // TODO : Security Context 적용 후, MemberModifyDetailRequest 에 Email 추가 필요
    @PostMapping("/modify-detail")
    public ResponseEntity<Object> modifyMemberDetail(@RequestBody MemberModifyDetailRequest request) {
        memberService.modifyMemberDetail(request.toService());
        return ResponseEntity.ok().body(null);
    }

    // TODO : Security Context 적용 후 구현
    @DeleteMapping("/withdraw")
    public ResponseEntity<Object> withdraw() {
        return ResponseEntity.ok().body(null);
    }
}
