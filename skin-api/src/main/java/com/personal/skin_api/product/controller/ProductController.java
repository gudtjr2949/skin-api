package com.personal.skin_api.product.controller;

import com.personal.skin_api.common.dto.CommonResponse;
import com.personal.skin_api.product.controller.dto.request.ProductFindListRequest;
import com.personal.skin_api.product.controller.dto.request.ProductRegisterRequest;
import com.personal.skin_api.product.service.ProductService;

import com.personal.skin_api.product.service.dto.request.ProductFindMyListServiceRequest;
import com.personal.skin_api.product.service.dto.response.ProductListResponse;
import com.personal.skin_api.product.service.dto.response.ProductDetailResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> registerProduct(@ModelAttribute ProductRegisterRequest request,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        productService.registerProduct(request.toService(userDetails.getUsername()));

        return ResponseEntity.ok().body(new CommonResponse(HttpStatus.OK.value(), "제품 등록 완료"));
    }

    @PostMapping("/list")
    public ResponseEntity<ProductListResponse> findProductList(@RequestBody ProductFindListRequest request) {
        return ResponseEntity.ok().body(productService.findProducts(request.toService()));
    }

    @GetMapping("/my-product")
    public ResponseEntity<ProductListResponse> findMyProductList(@RequestParam(value = "productId", defaultValue = "0") Long productId,
                                                                 @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok().body(productService.findMyProducts(ProductFindMyListServiceRequest.builder()
                .email(userDetails.getUsername())
                .productId(productId)
                .build()));
    }

    @GetMapping("/detail")
    public ResponseEntity<ProductDetailResponse> findProductDetail(@RequestParam("productId") Long productId) {
        return ResponseEntity.ok().body(productService.findProductDetail(productId));
    }
}
