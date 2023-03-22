package com.module2.shared.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.module2.shared.error.ErrorMessage;
import com.module2.shared.error.ErrorModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException {
        ErrorModel forbidden;
        if (request.getServletPath().equals("/api/login")) {
            forbidden = new ErrorModel(HttpStatus.FORBIDDEN, ErrorMessage.INVALID_USERNAME_OR_PASSWORD.getMessage());
        } else {
            forbidden = new ErrorModel(HttpStatus.FORBIDDEN, ErrorMessage.NEED_TO_LOGIN.getMessage());
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.writeValue(outputStream, forbidden);
        outputStream.flush();
    }
}
