package com.personal.skin_api.order.service.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCompleteServiceRequest {
    private String orderUid;
    private String email;
    private String impUid;

    @Builder
    private OrderCompleteServiceRequest(final String orderUid, final String email, final String impUid) {
        this.orderUid = orderUid;
        this.email = email;
        this.impUid = impUid;
    }
}
