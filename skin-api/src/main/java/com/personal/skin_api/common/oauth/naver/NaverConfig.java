package com.personal.skin_api.common.oauth.naver;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Setter
@Configuration
@ConfigurationProperties(prefix = "naver")
public class NaverConfig {

    private String requestTokenUri;
    private String clientId;
    private String clientSecret;

    public String getRequestUrl(final String code) {
        return UriComponentsBuilder.fromHttpUrl(requestTokenUri)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", code)
                .toUriString();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
