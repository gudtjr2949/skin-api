package com.personal.skin_api.review.service;

import com.personal.skin_api.review.service.dto.request.ReviewCreateServiceRequest;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {
    void createReview(ReviewCreateServiceRequest request);
}