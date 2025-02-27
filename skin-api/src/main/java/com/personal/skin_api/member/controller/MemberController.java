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

    @PostMapping("/find-email")
    public ResponseEntity<MemberFindEmailResponse> findEmail(@RequestBody MemberFindEmailRequest request) {
        MemberFindEmailResponse response = memberService.findEmail(request.toService());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/find-password")
    public ResponseEntity<Object> findPassword(@RequestBody MemberFindPasswordRequest request) {
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

    // TODO : Security Context 적용 후, MemberModifyDetailRequest에 Email 추가 필요
    @PostMapping("/modify-detail")
    public ResponseEntity<Object> modifyMemberDetail(@RequestBody MemberModifyDetailRequest request) {
        memberService.modifyMemberDetail(request.toService());
        return ResponseEntity.ok().body(null);
    }

    // TODO : Security Context 적용 후 구현
    @GetMapping("/withdraw")
    public ResponseEntity<Object> withdraw() {

        return ResponseEntity.ok().body(null);
    }
}
