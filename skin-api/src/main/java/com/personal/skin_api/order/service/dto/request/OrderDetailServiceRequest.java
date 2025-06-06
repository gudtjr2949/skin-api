package com.personal.skin_api.order.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderDetailServiceRequest {
    private String orderUid;
    private String email;

    @Builder
    private OrderDetailServiceRequest(final String orderUid, final String email) {
        this.orderUid = orderUid;
        this.email = email;
    }
}
