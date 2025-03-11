package com.personal.skin_api.product.repository.entity;

import com.personal.skin_api.common.entity.BaseEntity;
import com.personal.skin_api.product.repository.entity.price.Price;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    private String fileUrl;

    @Embedded
    private Price price;

    @Builder
    private Product(final Long id, final String fileUrl, final Price price) {
        this.id = id;
        this.fileUrl = fileUrl;
        this.price = price;
    }
}
