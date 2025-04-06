package com.personal.skin_api.product.controller;

import com.personal.skin_api.common.dto.CommonResponse;
import com.personal.skin_api.product.controller.dto.request.ProductFindListRequest;
import com.personal.skin_api.product.controller.dto.request.ProductModifyRequest;
import com.personal.skin_api.product.controller.dto.request.ProductRegisterRequest;
import com.personal.skin_api.product.service.ProductService;

import com.personal.skin_api.product.service.dto.request.ProductDeleteServiceRequest;
import com.personal.skin_api.product.service.dto.request.ProductFindMyListServiceRequest;
import com.personal.skin_api.product.service.dto.request.ProductRegisterServiceRequest;
import com.personal.skin_api.product.service.dto.response.ProductListResponse;
import com.personal.skin_api.product.service.dto.response.ProductDetailResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerProduct(@RequestParam("productName") String productName,
                                                          @RequestParam("productContent") String productContent,
                                                          @RequestParam("price") String price,
                                                          @RequestParam("blogUrl") String blogUrl,
                                                          @RequestParam("file") MultipartFile file,
                                                          @AuthenticationPrincipal UserDetails userDetails) {
        Long productId = productService.registerProduct(ProductRegisterServiceRequest.builder()
                .productName(productName)
                .productContent(productContent)
                .blogUrl(blogUrl)
                .price(Long.parseLong(price))
                .file(file)
                .email(userDetails.getUsername())
                .build());

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        return ResponseEntity.ok().body(map);
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

    @PostMapping("/modify")
    public ResponseEntity<CommonResponse> modifyProduct(@ModelAttribute ProductModifyRequest request,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        productService.modifyProduct(request.toService(userDetails.getUsername()));

        return ResponseEntity.ok().body(new CommonResponse(HttpStatus.OK.value(), "제품 수정 완료"));
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteProduct(@RequestParam("productId") Long productId,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        productService.deleteProduct(ProductDeleteServiceRequest.builder()
                .email(userDetails.getUsername())
                .productId(productId)
                .build());

        return ResponseEntity.ok().body(new CommonResponse(HttpStatus.OK.value(), "제품 삭제 완료"));
    }
}
