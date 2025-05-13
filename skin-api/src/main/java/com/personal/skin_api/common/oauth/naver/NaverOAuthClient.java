package com.personal.skin_api.common.oauth.naver;

import com.personal.skin_api.common.exception.CommonErrorCode;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.common.oauth.OAuthClient;
import com.personal.skin_api.common.oauth.naver.dto.response.NaverMemberResponse;
import com.personal.skin_api.common.oauth.naver.dto.response.OAuthMemberInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverOAuthClient implements OAuthClient {

    private final RestTemplate restTemplate;

    @Override
    public OAuthMemberInfoResponse requestMemberInfo(final String oauthAccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(oauthAccessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<NaverMemberResponse> response = null;
        try {
            response = restTemplate.exchange("https://openapi.naver.com/v1/nid/me", HttpMethod.GET, request, NaverMemberResponse.class);
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }

        NaverMemberResponse.NaverMemberDetail memberDetail = response.getBody().getNaverMemberDetail();

        log.info("naver memberDetail = {}", memberDetail);

        return makeResponse(memberDetail);
    }

    private static OAuthMemberInfoResponse makeResponse(NaverMemberResponse.NaverMemberDetail memberDetail) {
        return OAuthMemberInfoResponse.builder()
                .email(memberDetail.getEmail())
                .memberName(memberDetail.getName())
                .phone(memberDetail.getPhone())
                .build();
    }
}
