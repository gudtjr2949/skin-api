package com.personal.skin_api.payment.controller;

import com.personal.skin_api.common.dto.CommonResponse;
import com.personal.skin_api.payment.controller.dto.request.PaymentCreateRequest;
import com.personal.skin_api.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<Object> createPayment(@RequestBody PaymentCreateRequest request,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        paymentService.createPayment(request.toService(userDetails.getUsername()));
        return ResponseEntity.ok().body(new CommonResponse(200, "결제 정보 저장 성공"));
    }
}
