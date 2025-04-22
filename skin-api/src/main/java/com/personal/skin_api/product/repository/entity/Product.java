package com.personal.skin_api.product.repository.entity;

import com.personal.skin_api.chat.repository.entity.ChatRoom;
import com.personal.skin_api.common.entity.BaseEntity;
import com.personal.skin_api.member.repository.entity.Member;

import com.personal.skin_api.product.repository.entity.blogurl.BlogUrl;
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

    @Column(name = "ORDER_CNT")
    private int orderCnt;

    @Column(name = "REVIEW_CNT")
    private int reviewCnt;

    @Embedded
    private BlogUrl blogUrl;

    @Embedded
    private FileUrl fileUrl;

    @Column(name = "THUMBNAIL_URL")
    private String thumbnailUrl;

    @Embedded
    private Price price;

    @Embedded
    private ProductViews productViews;

    @OneToOne(mappedBy = "product")
    private ChatRoom chatRoom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "PRODUCT_STATUS")
    private ProductStatus productStatus;

    @Builder
    private Product(final Member member,
                    final String productName,
                    final String productContent,
                    final String blogUrl,
                    final String fileUrl,
                    final String thumbnailUrl,
                    final Long price) {
        this.member = member;
        this.productName = new ProductName(productName);
        this.productContent = new ProductContent(productContent);
        this.blogUrl = new BlogUrl(blogUrl);
        this.fileUrl = new FileUrl(fileUrl);
        this.thumbnailUrl = thumbnailUrl;
        this.price = new Price(price);
        this.productViews = new ProductViews(0);
        this.productStatus = ProductStatus.ACTIVE;
    }

    public void modifyProduct(final String productName,
                              final String productContent,
                              final String blogUrl,
                              final String fileUrl,
                              final Long price) {
        this.productName = new ProductName(productName);
        this.productContent = new ProductContent(productContent);
        this.blogUrl = new BlogUrl(blogUrl);
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

    public void increaseOrder() {
        orderCnt = orderCnt+1;
    }

    public void decreaseOrder() {
        if (orderCnt-1 <= 0) orderCnt = 0;
        else orderCnt = orderCnt-1;
    }

    public void increaseReview() {
        reviewCnt = reviewCnt+1;
    }

    public void decreaseReview() {
        if (reviewCnt-1 <= 0) reviewCnt = 0;
        else reviewCnt = reviewCnt-1;
    }

    public Long getId() {
        return id;
    }

    public String getMember() {
        return member.getEmail();
    }

    public String getMemberNickname() {
        return member.getNickname();
    }

    public String getProductName() {
        return productName.getProductName();
    }

    public String getProductContent() {
        return productContent.getProductContent();
    }

    public String getBlogUrl() {
        return blogUrl.getBlogUrl();
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

    public int getOrderCnt() {
        return orderCnt;
    }

    public int getReviewCnt() {
        return reviewCnt;
    }

    public Long getChatRoomId() {
        return chatRoom.getId();
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
