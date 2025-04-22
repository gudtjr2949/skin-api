package com.personal.skin_api.product.service;

import com.personal.skin_api.chat.service.ChatService;
import com.personal.skin_api.chat.service.dto.request.ChatRoomCreateServiceRequest;
import com.personal.skin_api.common.exception.CommonErrorCode;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.exception.member.MemberErrorCode;
import com.personal.skin_api.common.exception.product.ProductErrorCode;
import com.personal.skin_api.common.file.service.S3Service;
import com.personal.skin_api.member.repository.MemberRepository;
import com.personal.skin_api.member.repository.entity.Member;
import com.personal.skin_api.member.repository.entity.email.Email;
import com.personal.skin_api.product.repository.ProductRepository;
import com.personal.skin_api.product.repository.QProductRepository;
import com.personal.skin_api.product.repository.entity.Product;
import com.personal.skin_api.product.service.dto.request.*;
import com.personal.skin_api.product.service.dto.response.ProductDetailResponse;
import com.personal.skin_api.product.service.dto.response.ProductListResponse;
import com.personal.skin_api.product.service.dto.response.ProductResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.personal.skin_api.product.repository.QProductRepository.PRODUCTS_PAGE_SIZE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final S3Service s3Service;
    private final ChatService chatService;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final QProductRepository qProductRepository;

    /**
     * 제품을 등록한다.
     * @param request 등록 제품 정보
     */
    @Override
    @Transactional
    public Long registerProduct(ProductRegisterServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        String fileUrl = s3Service.uploadFile(request.getFile());
        String thumbnailUrl = s3Service.uploadFile(request.getThumbnail());

        Product product = request.toEntity(member, fileUrl, thumbnailUrl);

        try {
            productRepository.save(product);
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.DB_ERROR);
        }

        chatService.createChatRoom(ChatRoomCreateServiceRequest.builder()
                .product(product)
                .build());

        return product.getId();
    }

    /**
     * 제품 리스트를 조회한다.
     * @param request 제품 리스트 조회에 필요한 정보
     * @return 제품 리스트
     */
    @Override
    public ProductListResponse findProducts(ProductFindListServiceRequest request) {
        List<Product> products = qProductRepository.findProducts(request.getProductId(), request.getSorter(), request.getKeyword(), request.getLastSortValue());

        List<ProductResponse> productResponses = products.stream()
                .map(product -> ProductResponse.builder()
                        .productId(product.getId())
                        .productName(product.getProductName())
                        .price(product.getPrice())
                        .thumbnailUrl(product.getThumbnailUrl())
                        .orderCnt(product.getOrderCnt())
                        .reviewCnt(product.getReviewCnt())
                        .build())
                .toList();

        return new ProductListResponse(productResponses);
    }

    @Override
    public ProductListResponse findProductsWithOffset(ProductFindListServiceRequest request) {
        Pageable pageable = PageRequest.of(0, PRODUCTS_PAGE_SIZE);

        Page<Product> products = productRepository.findByIdGreaterThan(request.getProductId(), pageable);

        List<ProductResponse> productResponses = products.stream()
                .map(product -> ProductResponse.builder()
                        .productId(product.getId())
                        .productName(product.getProductName())
                        .price(product.getPrice())
                        .thumbnailUrl(product.getThumbnailUrl())
                        .orderCnt(product.getOrderCnt())
                        .reviewCnt(product.getReviewCnt())
                        .build())
                .toList();

        return new ProductListResponse(productResponses);
    }

    /**
     * 로그인한 사용자가 등록한 제품을 조회한다.
     * @param request 사용자가 등록한 제품 리스트 조회에 필요한 정보
     * @return 사용자가 등록한 제품 리스트
     */
    @Override
    public ProductListResponse findMyProducts(ProductFindMyListServiceRequest request) {
        Member findMember = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        List<Product> products = qProductRepository.findMyProducts(request.getProductId(), findMember);

        List<ProductResponse> productResponses = products.stream()
                .map(product -> ProductResponse.builder()
                        .productId(product.getId())
                        .productName(product.getProductName())
                        .price(product.getPrice())
                        .build())
                .toList();

        return new ProductListResponse(productResponses);
    }

    /**
     * 한 제품을 상세 조회한다.
     * @param productId 상세 조회할 제품 ID
     * @return 제품 상세 정보
     */
    @Override
    @Transactional
    public ProductDetailResponse findProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RestApiException(ProductErrorCode.PRODUCT_NOT_FOUND));

        product.increaseViews();

        return ProductDetailResponse.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .productContent(product.getProductContent())
                .blogUrl(product.getBlogUrl())
                .registrantNickname(product.getMember())
                .price(product.getPrice())
                .productViews(product.getProductViews())
                .chatRoomId(product.getChatRoomId())
                .build();
    }

    /**
     * 제품 정보를 수정한다.
     * @param request 수정할 제품 정보
     */
    @Override
    @Transactional
    public void modifyProduct(ProductModifyServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RestApiException(ProductErrorCode.PRODUCT_NOT_FOUND));

        if (!product.getMember().equals(member.getEmail())) {
            throw new RestApiException(ProductErrorCode.CAN_NOT_MODIFY_PRODUCT);
        }

        String newFileUrl = product.getFileUrl();

        if (request.getNewFile() != null) {
            newFileUrl = s3Service.uploadFile(request.getNewFile());
        }

        product.modifyProduct(request.getNewProductName(), request.getNewProductContent(), request.getNewBlogUrl(), newFileUrl, request.getNewPrice());
    }

    @Override
    @Transactional
    public void deleteProduct(ProductDeleteServiceRequest request) {
        Member member = memberRepository.findMemberByEmail(new Email(request.getEmail()))
                .orElseThrow(() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RestApiException(ProductErrorCode.PRODUCT_NOT_FOUND));

        if (!product.getMember().equals(member.getEmail())) {
            throw new RestApiException(ProductErrorCode.CAN_NOT_MODIFY_PRODUCT);
        }

        chatService.deleteChatRoom(product);
        product.deleteProduct();
    }
}
