package com.vireak.bidding.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public final String[] AUTH_WHITELIST = {
            "/","/favicon.ico", "/login", "/logout", "/css/**", "/public/**", "/register"
    };

    //private final AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        /*.requestMatchers("/api/v1/bids/**", "/api/v1/sessions/**")
                        .hasAnyRole(ADMIN.name(), BIDDER.name())
                        .requestMatchers("/api/v1/items/**")
                        .hasAnyRole(ADMIN.name(), USER.name(), BIDDER.name())
                        .requestMatchers("/api/v1/users**")
                        .hasAnyRole(ADMIN.name())
                        .requestMatchers("/api/**")
                        .hasAnyRole(ADMIN.name())*/
                        .anyRequest()
                        .authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                ;
        return http.build();
    }
}