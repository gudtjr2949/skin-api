package com.personal.skin_api.order.service.dto.response;

import lombok.Builder;

public class OrderLineResponse {
    private String productName;
    private Long price;
    private String orderUid;
    private String orderStatus;

    @Builder
    private OrderLineResponse(final String productName,
                              final Long price,
                              final String orderUid,
                              final String orderStatus) {
        this.productName = productName;
        this.price = price;
        this.orderUid = orderUid;
        this.orderStatus = orderStatus;
    }
}
