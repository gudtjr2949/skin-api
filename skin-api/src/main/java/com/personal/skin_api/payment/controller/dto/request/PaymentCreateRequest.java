package com.personal.skin_api.payment.controller.dto.request;

import com.personal.skin_api.payment.service.dto.request.PaymentCreateServiceRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreateRequest {
    private String impUid;
    private String orderUid;
    private String payMethod;
    private Long price;
    private LocalDateTime paidAt;

    public PaymentCreateServiceRequest toService(String email) {
        return PaymentCreateServiceRequest.builder()
                .email(email)
                .impUid(impUid)
                .orderUid(orderUid)
                .price(price)
                .payMethod(payMethod)
                .paidAt(paidAt)
                .build();
    }
}
