package com.personal.skin_api.chat.repository.entity;

import com.personal.skin_api.chat.repository.entity.room_title.ChatRoomTitle;
import com.personal.skin_api.common.entity.BaseEntity;
import com.personal.skin_api.product.repository.entity.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ChatRoom extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Embedded
    private ChatRoomTitle chatRoomTitle;


    @Builder
    private ChatRoom(final Product product) {
        this.product = product;
        this.chatRoomTitle = new ChatRoomTitle(product.getProductName());
    }

    public Long getId() {
        return id;
    }

    public String getChatRoomTitle() {
        return chatRoomTitle.getChatRoomTitle();
    }
}
