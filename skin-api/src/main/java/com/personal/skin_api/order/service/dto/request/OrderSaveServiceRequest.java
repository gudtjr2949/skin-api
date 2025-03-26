package com.personal.skin_api.order.service.dto.request;

import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.order.repository.entity.Payment;
import com.personal.skin_api.product.repository.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderSaveServiceRequest {
    private Long productId;
    private String email;
    private String impUid;

    @Builder
    private OrderSaveServiceRequest(final Long productId, final String email, final String impUid) {
        this.productId = productId;
        this.email = email;
        this.impUid = impUid;
    }

    Order toEntity(final Member member, final Product product, final Payment payment) {
        return Order.completedPayOrder(member, product, payment);
    }
}
