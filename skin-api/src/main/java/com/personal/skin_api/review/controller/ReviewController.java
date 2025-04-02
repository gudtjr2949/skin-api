package com.personal.skin_api.review.controller;

import com.personal.skin_api.common.dto.CommonResponse;
import com.personal.skin_api.review.controller.dto.request.ReviewCreateRequest;
import com.personal.skin_api.review.controller.dto.request.ReviewFindListRequest;
import com.personal.skin_api.review.controller.dto.request.ReviewModifyRequest;
import com.personal.skin_api.review.service.ReviewService;
import com.personal.skin_api.review.service.dto.request.ReviewDeleteServiceRequest;
import com.personal.skin_api.review.service.dto.response.ReviewListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<Object> createReview(@RequestBody ReviewCreateRequest request,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        reviewService.createReview(request.toService(userDetails.getUsername()));

        return ResponseEntity.ok().body(new CommonResponse(200, "후기 작성 성공"));
    }

    @PostMapping("/list")
    public ResponseEntity<ReviewListResponse> findReviewList(@RequestBody ReviewFindListRequest request) {
        ReviewListResponse response = reviewService.findReviewList(request.toService());

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/modify")
    public ResponseEntity<Object> modifyReview(@RequestBody ReviewModifyRequest request,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        reviewService.modifyReview(request.toService(userDetails.getUsername()));

        return ResponseEntity.ok().body(new CommonResponse(200, "후기 수정 성공"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteReview(@RequestParam Long reviewId,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        reviewService.deleteReview(ReviewDeleteServiceRequest.builder()
                .reviewId(reviewId)
                .email(userDetails.getUsername())
                .build());

        return ResponseEntity.ok().body(new CommonResponse(200, "후기 삭제 성공"));
    }
}
