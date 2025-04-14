package com.personal.skin_api.order.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WritableReviewOrderServiceRequest {
    private Long orderId;
    private String email;

    @Builder
    private WritableReviewOrderServiceRequest(final Long orderId,
                                              final String email) {
        this.orderId = orderId;
        this.email = email;
    }
}
