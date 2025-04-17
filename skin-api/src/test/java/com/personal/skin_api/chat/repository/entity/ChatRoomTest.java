package com.personal.skin_api.chat.repository.entity;

import com.personal.skin_api.EntityAbstractIntegrationTest;
import com.personal.skin_api.product.repository.entity.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ChatRoomTest extends EntityAbstractIntegrationTest {

    @Test
    void 채팅방을_생성한다() {
        // given
        Product product = createProduct(createGeneralMember());

        // when
        ChatRoom chatRoom = ChatRoom.builder()
                .product(product)
                .build();

        // then
        assertThat(chatRoom).isNotNull();
    }
}