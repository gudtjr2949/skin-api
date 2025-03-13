package com.personal.skin_api.common.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.skin_api.common.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response = setResponse(response);
    }

    private HttpServletResponse setResponse(HttpServletResponse response) throws IOException {
        HttpServletResponse newResponse = new HttpServletResponseWrapper(response);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "인증되지 않은 유저입니다.");
        newResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        newResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        newResponse.setCharacterEncoding("UTF-8");
        newResponse.getWriter().write(objectMapper.writeValueAsString(errorResponse));

        return newResponse;
    }
}
