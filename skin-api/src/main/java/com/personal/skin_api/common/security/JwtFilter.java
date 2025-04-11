package com.personal.skin_api.common.security;

import com.personal.skin_api.common.exception.ErrorCode;
import com.personal.skin_api.common.exception.GlobalExceptionHandler;
import com.personal.skin_api.common.exception.JwtErrorCode;
import com.personal.skin_api.common.exception.RestApiException;
import com.personal.skin_api.member.repository.entity.MemberRole;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import static com.personal.skin_api.common.security.JwtTokenConstant.secretKey;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final List<String> EXCLUDE_URLS = List.of(
            "/actuator/prometheus",
            "/api/v1/members/request-cert-code-signup-email",
            "/api/v1/members/check-cert-code-signup-email",
            "/api/v1/members/request-cert-code-signup-phone",
            "/api/v1/members/check-cert-code-signup-phone",
            "/api/v1/members/signup",
            "/api/v1/members/check-duplicated-nickname",
            "/api/v1/members/login",
            "/api/v1/members/reissue-access-token",
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
        try {
            String token = resolveToken(request);

            validate(token);

            String email = getEmailFromToken(token);

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(MemberRole.GENERAL.toString()));
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    email,
                    "null",
                    authorities);

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

            // 접근한 유저의 authentication 객체를 SecurityContextHolder 에 저장함
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (RestApiException e) {
            FilterExceptionHandler.handleExceptionInternal(response, e.getErrorCode());
        }
    }

    public static String getEmailFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }


    private boolean validate(final String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new RestApiException(JwtErrorCode.EXPIRED_JWT);
        } catch (Exception e) {
            throw new RestApiException(JwtErrorCode.INVALID_JWT);
        }
    }


    private String resolveToken(HttpServletRequest request) {
        try {
            String accessToken = Arrays.stream(request.getCookies())
                    .filter(cookie -> "accessToken".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst().orElseThrow(() -> new RestApiException(JwtErrorCode.INVALID_JWT));
            return accessToken;
        } catch (NullPointerException e) {
            throw new RestApiException(JwtErrorCode.JWT_CANNOT_BE_NULL);
        }
    }
}
