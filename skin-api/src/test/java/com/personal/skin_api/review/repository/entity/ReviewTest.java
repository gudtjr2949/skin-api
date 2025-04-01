package com.personal.skin_api.review.repository.entity;

import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.review.repository.entity.review_content.ReviewContent;

import org.junit.jupiter.api.Test;

import static com.personal.skin_api.review.repository.entity.review_content.ReviewContentLengthStrategy.*;
import static org.assertj.core.api.Assertions.*;

class ReviewTest {

    @Test
    void 후기_내용이_NULL_이라면_예외가_발생한다() {
        // given
        String nullContent = null;

        // when & then
        assertThatThrownBy(() -> new ReviewContent(nullContent))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void 후기_내용이_비어있는_경우_예외가_발생한다() {
        // given
        String emptyContent = "";

        // when & then
        assertThatThrownBy(() -> new ReviewContent(emptyContent))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void 공백을_제외한_후기_내용의_길이가_최소길이_이하인_경우_예외가_발생한다() {
        // given
        String shortContent = "1 ".repeat(REVIEW_CONTENT_MIN_LENGTH-1);

        // when & then
        assertThatThrownBy(() -> new ReviewContent(shortContent))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void 공백을_제외한_후기_내용의_길이가_최대길이_이상인_경우_예외가_발생한다() {
        // given
        String longContent = "1 ".repeat(REVIEW_CONTENT_MAX_LENGTH+1);

        // when & then
        assertThatThrownBy(() -> new ReviewContent(longContent))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void 후기가_정상적으로_생성된다() {
        // given
        String normalContent = "너무 좋은 스킨이네요! 잘 쓸께요!";

        // when
        ReviewContent reviewContent = new ReviewContent(normalContent);

        // then
        assertThat(reviewContent.getReviewContent()).isEqualTo(normalContent);
    }
}