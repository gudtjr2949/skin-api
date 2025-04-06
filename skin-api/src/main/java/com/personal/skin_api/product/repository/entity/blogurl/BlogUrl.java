package com.personal.skin_api.product.repository.entity.blogurl;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class BlogUrl {

    @Column(name = "BLOG_URL")
    private String blogUrl;

    public BlogUrl(final String blogUrl) {
        validate(blogUrl);
        this.blogUrl = blogUrl;
    }

    private void validate(final String blogUrl) {
        BlogUrlStrategyContext.runStrategy(blogUrl);
    }
}
