package com.personal.skin_api.common.oauth;

import com.personal.skin_api.common.oauth.naver.dto.response.OAuthMemberInfoResponse;

public interface OAuthClient {
    OAuthMemberInfoResponse requestMemberInfo(String oauthAccessToken);
}
