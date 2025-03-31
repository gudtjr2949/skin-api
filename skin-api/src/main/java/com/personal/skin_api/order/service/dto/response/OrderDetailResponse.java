package com.personal.skin_api.order.service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderDetailResponse {
    private String orderUid;
    private String memberName;
    private String productName;
    private Long price;
    private String payMethod;
    private LocalDateTime createdAt;

    @Builder
    public OrderDetailResponse(final String orderUid, final String memberName, final String productName,
                               final Long price, final String payMethod, final LocalDateTime createdAt) {
        this.orderUid = orderUid;
        this.memberName = memberName;
        this.productName = productName;
        this.price = price;
        this.payMethod = payMethod;
        this.createdAt = createdAt;
    }
}
