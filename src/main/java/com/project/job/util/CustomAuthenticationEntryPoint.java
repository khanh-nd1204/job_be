package com.project.job.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.job.dto.ResponseObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();

    private final ObjectMapper mapper;

    public CustomAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        this.delegate.commence(request, response, authException);
        if (response.isCommitted()) {
            return;
        }
        response.setContentType("application/json;charset=UTF-8");
        ResponseObject res = new ResponseObject();
        res.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        String errorMessage = authException.getCause() != null ? authException.getCause().getMessage() : "Invalid token";
        res.setMessage(errorMessage);
        res.setError("Authentication error");
        mapper.writeValue(response.getWriter(), res);
    }
}