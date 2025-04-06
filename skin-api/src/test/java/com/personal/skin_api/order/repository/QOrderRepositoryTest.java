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
}