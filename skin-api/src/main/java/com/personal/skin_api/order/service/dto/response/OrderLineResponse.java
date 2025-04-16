package com.personal.skin_api.order.service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderLineResponse {
    private String productName;
    private Long price;
    private String orderUid;
    private String orderStatus;
    private LocalDateTime createdAt;

    @Builder
    private OrderLineResponse(final String productName,
                              final Long price,
                              final String orderUid,
                              final String orderStatus,
                              final LocalDateTime createdAt) {
        this.productName = productName;
        this.price = price;
        this.orderUid = orderUid;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }
}
