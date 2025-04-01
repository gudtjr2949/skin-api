package com.personal.skin_api.payment.service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentDetailResponse {
    private Long price;
    private String impUid;
    private String payMethod;
    private LocalDateTime paidAt;

    @Builder
    private PaymentDetailResponse(final Long price, final String impUid, final String payMethod, final LocalDateTime paidAt) {
        this.price = price;
        this.impUid = impUid;
        this.payMethod = payMethod;
        this.paidAt = paidAt;
    }
}
