package com.personal.skin_api.product.repository.entity;

import com.personal.skin_api.common.entity.BaseEntity;
import com.personal.skin_api.member.repository.entity.Member;

import com.personal.skin_api.product.repository.entity.fileurl.FileUrl;
import com.personal.skin_api.product.repository.entity.price.Price;
import com.personal.skin_api.product.repository.entity.product_content.ProductContent;
import com.personal.skin_api.product.repository.entity.product_name.ProductName;
import com.personal.skin_api.product.repository.entity.views.ProductViews;
import jakarta.persistence.*;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Embedded
    private ProductName productName;

    @Embedded
    private ProductContent productContent;

    @Embedded
    private FileUrl fileUrl;

    @Embedded
    private Price price;

    @Embedded
    private ProductViews productViews;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "PRODUCT_STATUS")
    private ProductStatus productStatus;

    @Builder
    private Product(final Member member, final String productName, final String productContent, final String fileUrl, final Long price) {
        this.member = member;
        this.productName = new ProductName(productName);
        this.productContent = new ProductContent(productContent);
        this.fileUrl = new FileUrl(fileUrl);
        this.price = new Price(price);
        this.productViews = new ProductViews(0);
        this.productStatus = ProductStatus.ACTIVE;
    }

    public void modifyProduct(final String productName, final String productContent,
                              final String fileUrl, final Long price) {
        this.productName = new ProductName(productName);
        this.productContent = new ProductContent(productContent);
        this.fileUrl = new FileUrl(fileUrl);
        this.price = new Price(price);
    }

    public void increaseViews() {
        productViews.increaseViews();
    }

    public void deleteProduct() {
        this.productStatus = ProductStatus.DELETED;
    }

    public void reportProduct() {
        this.productStatus = ProductStatus.REPORTED;
    }



    public Long getId() {
        return id;
    }

    public String getMember() {
        return member.getEmail();
    }

    public String getProductName() {
        return productName.getProductName();
    }

    public String getProductContent() {
        return productContent.getProductContent();
    }

    public String getFileUrl() {
        return fileUrl.getFileUrl();
    }

    public Long getPrice() {
        return price.getPrice();
    }

    public int getProductViews() {
        return productViews.getProductViews();
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }
}
