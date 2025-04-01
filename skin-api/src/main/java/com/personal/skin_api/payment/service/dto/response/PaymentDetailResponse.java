package com.personal.skin_api.payment.service.dto.response;

import lombok.Builder;

public class PaymentDetailResponse {
    private Long price;
    private String payMethod;

    @Builder
    private PaymentDetailResponse(final Long price, final String payMethod) {
        this.price = price;
        this.payMethod = payMethod;
    }
}
