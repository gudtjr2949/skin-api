package com.personal.skin_api.order.repository;

import com.personal.skin_api.JpaAbstractIntegrationTest;
import com.personal.skin_api.common.util.MerchantUidGenerator;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.order.repository.entity.OrderStatus;
import com.personal.skin_api.payment.repository.PaymentRepository;
import com.personal.skin_api.payment.repository.entity.Payment;
import com.personal.skin_api.product.repository.ProductRepository;
import com.personal.skin_api.product.repository.entity.Product;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class OrderRepositoryTest extends JpaAbstractIntegrationTest {

    @Test
    void 주문을_생성하고_조회한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = Order.createBeforePayOrder(member, product, orderUid);

        // when
        orderRepository.save(order);
        Optional<Order> findOrder = orderRepository.findById(order.getId());

        // then
        assertThat(findOrder).isPresent();
        assertThat(findOrder.get().getId()).isEqualTo(order.getId());
        assertThat(findOrder.get().getOrderStatus()).isEqualTo(OrderStatus.WAITING);
    }

    @Test
    void 주문을_생성하고_주문_관련_정보를_조회한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = Order.createBeforePayOrder(member, product, orderUid);
        orderRepository.save(order);

        // when
        Optional<Order> findOrder = orderRepository.findById(order.getId());

        // then
        assertThat(findOrder).isPresent();
        assertThat(findOrder.get().getProductName()).isEqualTo(product.getProductName());
        assertThat(findOrder.get().getOrdererEmail()).isEqualTo(member.getEmail());
    }

    @Test
    void 주문을_취소한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = Order.createBeforePayOrder(member, product, orderUid);
        orderRepository.save(order);

        // when
        Optional<Order> findOrder = orderRepository.findById(order.getId());
        findOrder.get().cancelOrder();

        // then
        assertThat(findOrder).isPresent();
        assertThat(findOrder.get().getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
    }
}