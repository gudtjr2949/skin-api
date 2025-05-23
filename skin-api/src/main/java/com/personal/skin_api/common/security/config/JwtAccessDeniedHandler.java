package com.personal.skin_api.common.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.skin_api.common.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response = setResponse(response);
    }

    private HttpServletResponse setResponse(HttpServletResponse response) throws IOException {
        HttpServletResponse newResponse = new HttpServletResponseWrapper(response);

        ErrorResponse errorResultDto = new ErrorResponse(HttpStatus.FORBIDDEN.value(),"적절하지 않은 접근입니다.");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResultDto));

        return newResponse;
    }
}