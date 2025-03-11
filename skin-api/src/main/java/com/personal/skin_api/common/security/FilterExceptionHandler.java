package com.personal.skin_api.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.skin_api.common.exception.ErrorCode;
import com.personal.skin_api.common.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class FilterExceptionHandler {

    public static void handleExceptionInternal(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getHttpStatus().value(), errorCode.getMessage());
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
}
