package com.vireak.client_server.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Slf4j
public class ApplicationUtils {

    public static Boolean isConnect() {
        return Objects.nonNull(getConnectedUser());
    }

    public static LocalDateTime nowUTC() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public static Object getConnectedUser() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated()
        ) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        log.info(">getConnectedUser: {}", principal);
        return principal;
    }
}
