package com.personal.skin_api.order.controller.dto.request;

import com.personal.skin_api.order.service.dto.request.OrderListServiceRequest;
import lombok.Getter;

@Getter
public class OrderListRequest {
    private String keyword;
    private int year;
    private Long orderId;

    public OrderListServiceRequest toService(final String email) {
        return OrderListServiceRequest.builder()
                .orderId(orderId)
                .email(email)
                .keyword(keyword)
                .year(year)
                .build();
    }
}
