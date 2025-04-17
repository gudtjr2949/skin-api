package com.personal.skin_api.chat.service.dto.request;

import com.personal.skin_api.chat.repository.entity.ChatRoom;
import com.personal.skin_api.product.repository.entity.Product;
import lombok.Builder;
import lombok.Getter;

public class ChatRoomCreateServiceRequest {
    private Product product;

    @Builder
    public ChatRoomCreateServiceRequest(final Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .product(product)
                .build();
    }
}
