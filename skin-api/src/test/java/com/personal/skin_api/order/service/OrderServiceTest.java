package com.personal.skin_api.order.service;

import com.personal.skin_api.AbstractIntegrationTest;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.order.OrderErrorCode;
import com.personal.skin_api.common.util.MerchantUidGenerator;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.order.repository.OrderRepository;
import com.personal.skin_api.payment.repository.PaymentRepository;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.order.repository.entity.OrderStatus;
import com.personal.skin_api.payment.repository.entity.Payment;
import com.personal.skin_api.order.service.dto.request.OrderCreateBeforePaidServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderCreateTableServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderDetailServiceRequest;
import com.personal.skin_api.order.service.dto.request.OrderListServiceRequest;
import com.personal.skin_api.order.service.dto.response.OrderCreateTableResponse;
import com.personal.skin_api.order.service.dto.response.OrderDetailResponse;
import com.personal.skin_api.order.service.dto.response.OrderListResponse;
import com.personal.skin_api.product.repository.ProductRepository;
import com.personal.skin_api.product.repository.entity.Product;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.personal.skin_api.common.util.MerchantUidGenerator.makeFormattedDay;
import static com.personal.skin_api.order.repository.QOrderRepository.*;
import static org.assertj.core.api.Assertions.*;

class OrderServiceTest extends AbstractIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Test
    void 주문테이블을_생성한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);

        OrderCreateTableServiceRequest request = OrderCreateTableServiceRequest.builder()
                .email(member.getEmail())
                .productId(product.getId())
                .build();

        // when
        OrderCreateTableResponse orderTable = orderService.createOrderTable(request);

        // then
        assertThat(orderTable.getEmail()).isEqualTo(request.getEmail());
        assertThat(orderTable.getPrice()).isEqualTo(product.getPrice());
    }
    
    @Test
    void 결제_전_주문정보를_생성한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);

        OrderCreateBeforePaidServiceRequest request = OrderCreateBeforePaidServiceRequest.builder()
                .email(member.getEmail())
                .productId(product.getId())
                .build();
        
        // when
        String beforePaidOrder = orderService.createBeforePaidOrder(request);
        String today = makeFormattedDay();

        // then
        assertThat(beforePaidOrder).isNotNull();
        assertThat(beforePaidOrder.startsWith(today)).isTrue();
    }
    
    @Test
    void 주문정보를_상세조회한다() {
        // given
        Member member = createGeneralMember();
        Product product = createProduct(member);
        String orderUid = MerchantUidGenerator.generateMerchantUid();
        Order order = createOrder(member, product, orderUid);

        OrderDetailServiceRequest orderDetailServiceRequest = OrderDetailServiceRequest.builder()
                .orderUid(orderUid)
                .email(member.getEmail())
                .build();

        // when
        OrderDetailResponse detailOrder = orderService.findOrder(orderDetailServiceRequest);

        // then
        assertThat(detailOrder).isNotNull();
        assertThat(detailOrder.getOrderUid()).isEqualTo(orderUid);
    }

    @Test
    void 나의_주문_리스트를_조회한다() {
        // give
        Member member = createGeneralMember();
        Product product = createProduct(member);
        for (int i = 0 ; i < ORDER_PAGE_SIZE ; i++) {
            createOrder(member, product, MerchantUidGenerator.generateMerchantUid());
        }

        OrderListServiceRequest request = OrderListServiceRequest.builder()
                .orderId(0L)
                .keyword("")
                .year(LocalDateTime.now().getYear())
                .email(member.getEmail())
                .build();

        // when
        OrderListResponse myOrderList = orderService.findMyOrderList(request);

        // then
        assertThat(myOrderList).isNotNull();
        assertThat(myOrderList.getOrderResponses()).hasSize(ORDER_PAGE_SIZE);
    }
}