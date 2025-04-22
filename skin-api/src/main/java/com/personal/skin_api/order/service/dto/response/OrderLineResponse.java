package com.personal.skin_api.order.service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderLineResponse {
    private Long productId;
    private String productName;
    private String thumbnailUrl;
    private Long price;
    private String orderUid;
    private String orderStatus;
    private LocalDateTime createdAt;

    @Builder
    private OrderLineResponse(final Long productId,
                              final String productName,
                              final Long price,
                              final String thumbnailUrl,
                              final String orderUid,
                              final String orderStatus,
                              final LocalDateTime createdAt) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
        this.orderUid = orderUid;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }
}
