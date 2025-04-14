package com.personal.skin_api.order.service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WritableReviewOrderLineResponse {
    private String productName;
    private String orderUid;
    private LocalDateTime createdAt;

    @Builder
    private WritableReviewOrderLineResponse(final String productName,
                                            final String orderUid,
                                            final LocalDateTime createdAt) {
        this.productName = productName;
        this.orderUid = orderUid;
        this.createdAt = createdAt;
    }
}
