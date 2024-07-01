package com.vireak.authorization_server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull AuthenticationException exp)
            throws IOException, ServletException {
        //response.setContentType("application/json");
        //response.setStatus(SC_UNAUTHORIZED);
        Map<String,Object> mapResponse=new HashMap<>();
        mapResponse.put("status",SC_UNAUTHORIZED);
        mapResponse.put("message",exp.getMessage());
        //new ObjectMapper().writeValue(response.getOutputStream(),mapResponse);
        log.info(">CustomAuthenticationEntryPoint.commence: {}",mapResponse);
        response.getOutputStream().flush();
    }
}
