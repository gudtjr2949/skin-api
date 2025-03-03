package com.personal.skin_api.common.security;

import com.personal.skin_api.common.exception.JwtErrorCode;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.member.repository.entity.MemberRole;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import java.util.List;

import static com.personal.skin_api.common.security.JwtTokenConstant.secretKey;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final List<String> EXCLUDE_URLS = List.of(
            "/actuator/prometheus",
            "/api/v1/members/request-cert-code-signup-email",
            "/api/v1/members/check-cert-code-signup-email",
            "/api/v1/members/request-cert-code-signup-phone",
            "/api/v1/members/check-cert-code-signup-phone",
            "/api/v1/members/signup",
            "/api/v1/members/login",
            "/api/v1/members/request-cert-code-find-email",
            "/api/v1/members/check-cert-code-find-email",
            "/api/v1/members/request-cert-code-find-password",
            "/api/v1/members/check-cert-code-find-password",
            "/api/v1/members/modify-password"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return EXCLUDE_URLS.stream().anyMatch(requestURI::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        if (validate(token)) {

            String email = getEmailFromToken(token);

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(MemberRole.GENERAL.toString()));

            Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);

            // 접근한 유저의 authentication 객체를 SecurityContextHolder 에 저장함
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        }
    }

    private String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    private boolean validate(final String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new RestApiException(JwtErrorCode.INVALID_JWT);
        }
    }


    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
