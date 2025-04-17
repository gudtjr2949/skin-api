package com.personal.skin_api.chat.service.dto.request;

import lombok.Builder;
import lombok.Getter;

public class ChatRoomCreateServiceRequest {
    private Long productId;

    @Builder
    public ChatRoomCreateServiceRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
