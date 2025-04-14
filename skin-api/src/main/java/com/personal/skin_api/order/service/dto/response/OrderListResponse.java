package com.personal.skin_api.order.service.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderListResponse {
    private List<OrderLineResponse> orderResponses;

    public OrderListResponse(final List<OrderLineResponse> orderResponses) {
        this.orderResponses = orderResponses;
    }
}
