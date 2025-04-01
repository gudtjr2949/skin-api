package com.personal.skin_api.order.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderListServiceRequest {
    private Long orderId;
    private String keyword;
    private int year;
    private String email;

    @Builder
    private OrderListServiceRequest(final Long orderId, final String keyword, final int year, final String email) {
        this.orderId = orderId;
        this.keyword = keyword;
        this.year = year;
        this.email = email;
    }
}
