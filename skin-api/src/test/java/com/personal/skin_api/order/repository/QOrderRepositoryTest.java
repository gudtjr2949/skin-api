package com.personal.skin_api.order.repository;

import com.personal.skin_api.AbstractIntegrationTest;
import com.personal.skin_api.JpaAbstractIntegrationTest;
import com.personal.skin_api.common.util.MerchantUidGenerator;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.payment.repository.PaymentRepository;
import com.personal.skin_api.payment.repository.entity.Payment;

import com.personal.skin_api.product.repository.ProductRepository;
import com.personal.skin_api.product.repository.entity.Product;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.personal.skin_api.order.repository.QOrderRepository.ORDER_PAGE_SIZE;
import static org.assertj.core.api.Assertions.assertThat;

class QOrderRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private QOrderRepository qOrderRepository;

    @Test
    void 회원_정보가_일치하는_주문목록_첫_장을_조회한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);

        Order firstOrder = createOrder(member, product, MerchantUidGenerator.generateMerchantUid());
        for (int i = 1 ; i < ORDER_PAGE_SIZE-1 ; i++) {
            createOrder(member, product, MerchantUidGenerator.generateMerchantUid());
        }
        Order lastOrder = createOrder(member, product, MerchantUidGenerator.generateMerchantUid());

        // when
        List<Order> myOrders = qOrderRepository.findMyOrderList(0L, member, "", LocalDateTime.now().getYear());

        // then
        assertThat(myOrders).hasSize(ORDER_PAGE_SIZE);
        assertThat(myOrders.get(0).getId()).isEqualTo(lastOrder.getId());
        assertThat(myOrders.get(ORDER_PAGE_SIZE-1).getId()).isEqualTo(firstOrder.getId());
    }

    @Test
    void 주문과_결제를_완료했지만_후기를_작성하지_않은_주문_목록을_조회한다() {
        // given
        int reviewCnt = 5;
        Member member = createGeneralMemberWithEmail("tester@naver.com");

        for (int i = 0 ; i < reviewCnt ; i++) {
            Product product = createProduct(createGeneralMember());
            String orderUid = MerchantUidGenerator.generateMerchantUid();
            Order order = createPaidOrder(member, product, orderUid);
            createPayment(order);

            // 절반만 후기 작성
            if (i % 2 == 0) {
                createReview(member, product, order);
            }
        }

        // when
        List<Order> writableReviewsOrderList = qOrderRepository.findWritableReviewsOrderList(0L, member);

        // then
        assertThat(writableReviewsOrderList).hasSize(reviewCnt / 2);
    }

    @Test
    void 모든_주문의_리뷰를_작성했다면_조회되지_않는다() {
        // given
        int reviewCnt = 5;
        Member member = createGeneralMemberWithEmail("tester@naver.com");

        for (int i = 0 ; i < reviewCnt ; i++) {
            Product product = createProduct(createGeneralMember());
            String orderUid = MerchantUidGenerator.generateMerchantUid();
            Order order = createPaidOrder(member, product, orderUid);
            createPayment(order);
            createReview(member, product, order);
        }

        // when
        List<Order> writableReviewsOrderList = qOrderRepository.findWritableReviewsOrderList(0L, member);

        // then
        assertThat(writableReviewsOrderList).hasSize(0);
    }

    @Test
    void 주문_목록에서_결제가_완료되지_않은_제품은_조회_목록에서_제외된다() {
        // given
        int reviewCnt = 5;
        Member member = createGeneralMemberWithEmail("tester@naver.com");

        for (int i = 0 ; i < reviewCnt ; i++) {
            Product product = createProduct(createGeneralMember());
            String orderUid = MerchantUidGenerator.generateMerchantUid();
            if (i % 2 == 0) { // 결제 X
                Order order = createOrder(member, product, orderUid);
            } else { // 결제 O
                Order order = createPaidOrder(member, product, orderUid);
                createPayment(order);
            }
        }

        // when
        List<Order> writableReviewsOrderList = qOrderRepository.findWritableReviewsOrderList(0L, member);

        // then
        assertThat(writableReviewsOrderList).hasSize(reviewCnt / 2);
    }
}