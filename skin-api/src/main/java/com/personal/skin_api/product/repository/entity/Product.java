package com.personal.skin_api.product.repository.entity;

import com.personal.skin_api.common.entity.BaseEntity;
import com.personal.skin_api.product.repository.entity.fileurl.FileUrl;
import com.personal.skin_api.product.repository.entity.price.Price;
import com.personal.skin_api.product.repository.entity.product_content.ProductContent;
import jakarta.persistence.*;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Embedded
    private ProductContent productContent;

    @Embedded
    private FileUrl fileUrl;

    @Embedded
    private Price price;
}
