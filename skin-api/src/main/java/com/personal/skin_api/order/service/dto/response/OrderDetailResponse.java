package com.personal.skin_api.order.service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderDetailResponse {
    private Long orderId;
    private String email;
    private String memberName;
    private String nickname;
    private String productName;
    private Long price;
    private String paymentsMethod;
    private LocalDateTime createdAt;

    @Builder
    public OrderDetailResponse(final Long orderId, final String email, final String memberName,
                               final String nickname, final String productName, final Long price,
                               final String paymentsMethod, final LocalDateTime createdAt) {
        this.orderId = orderId;
        this.email = email;
        this.memberName = memberName;
        this.nickname = nickname;
        this.productName = productName;
        this.price = price;
        this.paymentsMethod = paymentsMethod;
        this.createdAt = createdAt;
    }
}
