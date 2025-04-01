package com.personal.skin_api.review.repository.entity;

import com.personal.skin_api.EntityAbstractIntegrationTest;
import com.personal.skin_api.common.util.MerchantUidGenerator;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.payment.repository.entity.Payment;
import com.personal.skin_api.product.repository.entity.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ReviewTest extends EntityAbstractIntegrationTest {

    @Test
    void 후기를_정상적으로_생성한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = createOrder(member, product, orderUid);
        Payment payment = createPayment(order);
        String reviewContent = "너무 좋은 스킨입니다! 잘 쓸께요!";

        // when
        Review review = Review.builder()
                .member(member)
                .product(product)
                .order(order)
                .reviewContent(reviewContent)
                .build();

        // then
        assertThat(review.getReviewContent()).isEqualTo(reviewContent);
    }

}