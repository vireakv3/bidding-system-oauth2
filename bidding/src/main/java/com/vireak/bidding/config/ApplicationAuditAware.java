package com.vireak.bidding.config;

import com.vireak.bidding.entity.User;
import com.vireak.bidding.repository.UserRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ApplicationAuditAware implements AuditorAware<Long> {

    private final UserRepository userRepository;
    private User user;

    @Override
    public @Nonnull Optional<Long> getCurrentAuditor() {
        /*Optional<Long> result = Optional.empty();
        User connectedUser = applicationUtils.getConnectedUser();
        if (Objects.nonNull(connectedUser)) result = Optional.ofNullable(connectedUser.getId());
        log.info(">ApplicationAuditAware.getCurrentAuditor: {}", result);
        return result;*/
        Optional<Long> result = Optional.empty();
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated()
        ) {
            return result;
        }
        log.info(">getCurrentAuditor.authentication: {}", authentication);
        if (Objects.nonNull(user) && user.getUsername().equals(authentication.getName()))
            return Optional.ofNullable(user.getId());
        this.user = userRepository.findByEmail(authentication.getName())
                .orElse(null);
        log.info(">getConnectedUser.user: {}", user);
        if (Objects.nonNull(user)) return Optional.ofNullable(user.getId());
        return result;
    }
}
