package com.personal.skin_api.order.service.dto.request;

import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.product.repository.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreateServiceRequest {
    private Long productId;
    private String email;

    @Builder
    private OrderCreateServiceRequest(final Long productId, final String email) {
        this.productId = productId;
        this.email = email;
    }


    Order toEntity(final Member member, final Product product) {
       return Order.createBeforePayOrder(member, product);
    }


}
