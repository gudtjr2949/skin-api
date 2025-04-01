package com.personal.skin_api.payment.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PaymentFindServiceRequest {
    private String orderUid;
    private String email;

    @Builder
    private PaymentFindServiceRequest(final String orderUid, final String email) {
        this.orderUid = orderUid;
        this.email = email;
    }
}
