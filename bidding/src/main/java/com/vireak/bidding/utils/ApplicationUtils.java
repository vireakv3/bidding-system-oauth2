package com.vireak.bidding.utils;

import com.vireak.bidding.entity.User;
import com.vireak.bidding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationUtils {

    private final UserRepository userRepository;
    public User user;

    public Boolean isConnect() {
        return Objects.nonNull(getConnectedUser());
    }

    public static LocalDateTime nowUTC() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public Long getConnectedUserId() {
        var u=getConnectedUser();
        return Objects.nonNull(u)?u.getId():null;
    }

    public User getConnectedUser() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated()
        ) {
            return null;
        }
        if (Objects.nonNull(user) && user.getUsername().equals(authentication.getName())) return user;
        log.info(">getConnectedUser.authentication: {}", authentication);
        this.user = userRepository.findByEmail(authentication.getName())
                .orElse(null);
        log.info(">getConnectedUser.user: {}", user);
        return this.user;
    }
}
