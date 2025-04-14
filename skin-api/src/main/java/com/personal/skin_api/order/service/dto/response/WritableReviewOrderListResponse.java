package com.personal.skin_api.order.service.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class WritableReviewOrderListResponse {
    private List<WritableReviewOrderLineResponse> orderResponses;

    public WritableReviewOrderListResponse(List<WritableReviewOrderLineResponse> orderResponses) {
        this.orderResponses = orderResponses;
    }
}
