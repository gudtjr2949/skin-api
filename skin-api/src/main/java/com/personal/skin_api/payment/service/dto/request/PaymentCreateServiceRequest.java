package com.personal.skin_api.payment.service.dto.request;

import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.payment.repository.entity.Payment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentCreateServiceRequest {
    private String impUid;
    private String orderUid;
    private String email;
    private String payMethod;
    private Long price;
    private LocalDateTime paidAt;

    @Builder
    private PaymentCreateServiceRequest(final String impUid, final String orderUid,
                                        final String email, final String payMethod,
                                        final Long price, final LocalDateTime paidAt) {
        this.impUid = impUid;
        this.orderUid = orderUid;
        this.email = email;
        this.payMethod = payMethod;
        this.price = price;
        this.paidAt = paidAt;
    }

    public Payment toEntity(final Order order) {
        return Payment.builder()
                .order(order)
                .impUid(impUid)
                .payMethod(payMethod)
                .price(price)
                .paidAt(paidAt)
                .build();
    }
}
