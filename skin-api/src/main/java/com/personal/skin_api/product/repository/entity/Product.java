package com.personal.skin_api.product.repository.entity;

import com.personal.skin_api.common.entity.BaseEntity;
import com.personal.skin_api.product.repository.entity.fileurl.FileUrl;
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

    @Embedded
    private FileUrl fileUrl;

    @Embedded
    private Price price;

    @Builder
    private Product(final String fileUrl, final int price) {
        this.fileUrl = new FileUrl(fileUrl);
        this.price = new Price(price);
    }
}
