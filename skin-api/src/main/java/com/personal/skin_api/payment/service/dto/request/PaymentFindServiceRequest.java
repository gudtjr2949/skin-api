package com.personal.skin_api.payment.service.dto.request;

import lombok.Builder;

public class PaymentFindServiceRequest {
    private Long orderId;
    private String email;

    @Builder
    private PaymentFindServiceRequest(final Long orderId, final String email) {
        this.orderId = orderId;
        this.email = email;
    }
}
