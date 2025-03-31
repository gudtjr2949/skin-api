package com.personal.skin_api.payment.service.dto.request;

import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.payment.repository.entity.Payment;
import lombok.Builder;

import java.time.LocalDateTime;

public class PaymentCreateServiceRequest {
    private String impUid;
    private String orderUid;
    private String payMethod;
    private Long price;
    private LocalDateTime paidAt;

    @Builder
    private PaymentCreateServiceRequest(final String impUid, final String orderUid,
                                        final String payMethod, final Long price,
                                        final LocalDateTime paidAt) {
        this.impUid = impUid;
        this.orderUid = orderUid;
        this.payMethod = payMethod;
        this.price = price;
        this.paidAt = paidAt;
    }

    Payment toEntity(final Order order) {
        return Payment.builder()
                .order(order)
                .impUid(impUid)
                .payMethod(payMethod)
                .price(price)
                .paidAt(paidAt)
                .build();
    }
}
