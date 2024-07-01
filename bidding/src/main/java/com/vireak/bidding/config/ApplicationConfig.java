package com.vireak.bidding.config;

import com.vireak.bidding.repository.UserRepository;
import com.vireak.bidding.service.UserService;
import com.vireak.bidding.utils.ApplicationUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
public class ApplicationConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetails(UserService userService) {
        return userService::findByEmail;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    //@Bean
    public AuditorAware<Long> auditorAware(UserRepository userRepository) {
        return new ApplicationAuditAware(userRepository);
    }
}
