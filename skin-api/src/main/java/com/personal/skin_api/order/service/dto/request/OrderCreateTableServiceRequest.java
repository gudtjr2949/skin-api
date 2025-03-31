package com.personal.skin_api.order.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreateTableServiceRequest {
    private Long productId;
    private String email;

    @Builder
    private OrderCreateTableServiceRequest(final Long productId, final String email) {
        this.productId = productId;
        this.email = email;
    }
}
