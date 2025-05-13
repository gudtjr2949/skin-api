package com.personal.skin_api.member.repository.entity;


public enum AuthProvider {
    LOCAL, NAVER, GOOGLE, KAKAO // 등등
    ;

    public static AuthProvider toAuthProvider(final String provider) {
        switch (provider) {
            case "NAVER":
                return AuthProvider.NAVER;
            case "KAKAO":
                return AuthProvider.KAKAO;
            case "GOOGLE":
                return AuthProvider.GOOGLE;
            default:
                return AuthProvider.LOCAL;
        }
    }
}
