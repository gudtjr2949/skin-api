package com.personal.skin_api.order.service.dto.response;

import lombok.Builder;

public class OrderCreateResponse {

    private String memberName;
    private String email;
    private String phone;
    private Long price;
    private Long orderId;

    @Builder
    private OrderCreateResponse(final String memberName, final String email,
                                final String phone, final Long price, final Long orderId) {
        this.memberName = memberName;
        this.email = email;
        this.phone = phone;
        this.price = price;
        this.orderId = orderId;
    }
}
