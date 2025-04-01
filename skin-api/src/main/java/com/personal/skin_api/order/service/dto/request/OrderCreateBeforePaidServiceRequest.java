package com.personal.skin_api.order.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreateBeforePaidServiceRequest {
    private String email;
    private Long productId;

    @Builder
    private OrderCreateBeforePaidServiceRequest(final String email, final Long productId) {
        this.email = email;
        this.productId = productId;
    }
}
