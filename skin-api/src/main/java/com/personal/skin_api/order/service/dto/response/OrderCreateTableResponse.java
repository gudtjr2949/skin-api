package com.personal.skin_api.order.service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreateTableResponse {

    private String memberName;
    private String email;
    private String phone;
    private Long price;

    @Builder
    private OrderCreateTableResponse(final String memberName, final String email,
                                     final String phone, final Long price) {
        this.memberName = memberName;
        this.email = email;
        this.phone = phone;
        this.price = price;
    }
}
