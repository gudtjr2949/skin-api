package com.personal.skin_api.order.service.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderListResponse {
    private List<OrderDetailResponse> orderResponses;

    public OrderListResponse(final List<OrderDetailResponse> orderResponses) {
        this.orderResponses = orderResponses;
    }
}
