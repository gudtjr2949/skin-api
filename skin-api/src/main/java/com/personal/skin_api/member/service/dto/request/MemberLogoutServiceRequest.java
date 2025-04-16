package com.personal.skin_api.member.service.dto.request;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLogoutServiceRequest {
    private String email;
    private HttpServletResponse response;

    @Builder
    private MemberLogoutServiceRequest(final String email,
                                       final HttpServletResponse response) {
        this.email = email;
        this.response = response;
    }
}
