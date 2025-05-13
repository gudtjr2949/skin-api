package com.personal.skin_api;

import com.personal.skin_api.chat.repository.ChatRepository;
import com.personal.skin_api.chat.repository.ChatRoomMemberRepository;
import com.personal.skin_api.chat.repository.ChatRoomRepository;
import com.personal.skin_api.chat.repository.MongoChatRepository;
import com.personal.skin_api.chat.repository.entity.Chat;
import com.personal.skin_api.chat.repository.entity.ChatRoom;
import com.personal.skin_api.chat.repository.entity.ChatRoomMember;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.MemberRole;
import com.personal.skin_api.member.repository.entity.MemberStatus;
import com.personal.skin_api.member.service.MemberPasswordEncryption;
import com.personal.skin_api.order.repository.OrderRepository;
import com.personal.skin_api.order.repository.entity.Order;
import com.personal.skin_api.payment.repository.PaymentRepository;
import com.personal.skin_api.payment.repository.entity.Payment;
import com.personal.skin_api.payment.service.PaymentService;
import com.personal.skin_api.product.repository.ProductRepository;
import com.personal.skin_api.product.repository.entity.Product;
import com.personal.skin_api.review.repository.ReviewRepository;
import com.personal.skin_api.review.repository.entity.Review;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected OrderRepository orderRepository;

    @Autowired
    protected PaymentRepository paymentRepository;

    @Autowired
    protected ReviewRepository reviewRepository;

    @Autowired
    protected ChatRepository chatRepository;

    @Autowired
    protected ChatRoomRepository chatRoomRepository;

    @Autowired
    protected ChatRoomMemberRepository chatRoomMemberRepository;

    @Autowired
    protected MongoChatRepository mongoChatRepository;

    @Autowired
    protected MemberPasswordEncryption memberPasswordEncryption;

    @AfterEach
    void tearDown() {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
        chatRoomMemberRepository.deleteAllInBatch();
        mongoChatRepository.deleteAll();
        chatRepository.deleteAllInBatch();
        chatRoomRepository.deleteAllInBatch();
        reviewRepository.deleteAllInBatch();
        paymentRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    /**
     * 회원 정보
     */
    private static String email = "asd123@naver.com";
    private static String password = "asd1234!";
    private static String memberName = "홍길동";
    private static String nickname = "길동짱짱";
    private static String phone = "01012345678";

    /**
     * 제품 정보
     */
    private static String productName = "형석이의 스킨";
    private static String productContent = "아주 예쁜 스킨입니다!";
    private static String blogUrl = "www.test-blog.com";
    private static String fileUrl = "s3://hyeongseok-skin/fileUrl";
    private static Long price = 10_000L;


    /**
     * 결제 정보
     */
    private static String impUid = "imp_123456789876";
    private static String payMethod = "card";

    /**
     * 후기 정보
     *
     */
    private static String reviewContent = "너무 좋은 스킨입니다! 감사합니다!";

    protected Member createGeneralMember() {
        return memberRepository.save(Member.builder()
                .email(email)
                .password(password)
                .memberName(memberName)
                .nickname(nickname)
                .phone(phone)
                .status(MemberStatus.ACTIVE)
                .role(MemberRole.GENERAL)
                .provider("LOCAL")
                .build());
    }

    protected Member createGeneralMemberWithEmail(final String email) {
        return memberRepository.save(Member.builder()
                .email(email)
                .password(password)
                .memberName(memberName)
                .nickname(nickname)
                .phone(phone)
                .status(MemberStatus.ACTIVE)
                .role(MemberRole.GENERAL)
                .provider("LOCAL")
                .build());
    }

    protected Member createGeneralMemberWithPassword(final String password) {
        return memberRepository.save(Member.builder()
                .email(email)
                .password(password)
                .memberName(memberName)
                .nickname(nickname)
                .phone(phone)
                .status(MemberStatus.ACTIVE)
                .role(MemberRole.GENERAL)
                .provider("LOCAL")
                .build());
    }

    protected Product createProduct(Member member) {
        return productRepository.save(Product.builder()
                .member(member)
                .productName(productName)
                .productContent(productContent)
                .blogUrl(blogUrl)
                .fileUrl(fileUrl)
                .price(price)
                .build());
    }

    protected Product createProductWithPrice(Member member, Long price) {
        return productRepository.save(Product.builder()
                .member(member)
                .productName(productName)
                .productContent(productContent)
                .blogUrl(blogUrl)
                .fileUrl(fileUrl)
                .price(price)
                .build());
    }

    protected Order createOrder(final Member member, final Product product, final String orderUid) {
        return orderRepository.save(Order.createBeforePayOrder(member, product, orderUid));
    }

    protected Order createPaidOrder(final Member member, final Product product, final String orderUid) {
        return orderRepository.save(Order.createPaidOrder(member, product, orderUid));
    }

    protected Payment createPayment(final Order order) {
        return paymentRepository.save(Payment.builder()
                .price(price)
                .paidAt(LocalDateTime.now())
                .payMethod(payMethod)
                .impUid(impUid)
                .order(order)
                .build());
    }

    protected Review createReview(final Member member, final Product product, final Order order) {
        return reviewRepository.save(Review.builder()
                .member(member)
                .product(product)
                .order(order)
                .reviewContent(reviewContent)
                .build());
    }

    protected ChatRoom createChatRoom(final Product product) {
        return chatRoomRepository.save(ChatRoom.builder()
                .product(product)
                .build());
    }

    protected Chat createChat(final ChatRoom chatRoom,
                              final Member member,
                              final String chatContent) {
        return chatRepository.save(Chat.builder()
                .chatRoom(chatRoom)
                .member(member)
                .chatContent(chatContent)
                .build());
    }

    protected ChatRoomMember createChatRoomMember(final ChatRoom chatRoom,
                                                  final Member member) {
        return chatRoomMemberRepository.save(ChatRoomMember.builder()
                .chatRoom(chatRoom)
                .member(member)
                .build());
    }
}
