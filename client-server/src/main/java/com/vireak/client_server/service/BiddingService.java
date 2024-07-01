package com.vireak.client_server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class BiddingService {

    @Value("${resourceServerUrl:http://localhost:2024}")
    private String baseUrl;

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final RestTemplate restTemplate = new RestTemplate();
    private String accessToken;
    private Principal principal;
    private HttpHeaders httpHeaders = new HttpHeaders();

    private void revokeAccessToken() {
        this.accessToken = authorizedClientService
                .loadAuthorizedClient("reg-client", principal.getName())
                .getAccessToken().getTokenValue();
        log.info(">>>>revokeAccessToken: {}", accessToken);
        this.httpHeaders.set("Authorization", "Bearer " + accessToken);
    }

    public ResponseEntity<?> callout(
            Principal principal,
            String paths,
            HttpMethod method) {
        return callout(principal, paths, method, null);
    }

    public ResponseEntity<?> callout(
            Principal principal,
            String paths,
            HttpMethod method,
            Object requestBody) {
        // clear headers
        httpHeaders = new HttpHeaders();
        if (Objects.nonNull(principal)) this.principal = principal;
        if (Strings.isBlank(this.accessToken)) this.revokeAccessToken();
        ResponseEntity<?> response;
        try {
            // first try to callout??
            response = callout(paths, method, requestBody);
            log.info(">>>>>>>>>>>>>>>>>>BiddingService(1).callout: {} {}", paths, response.getStatusCode());
            if (response.getStatusCode().is4xxClientError()) {
                // second try revoke token and call again ??
                this.revokeAccessToken();
                response = callout(paths, method, requestBody);
                log.info(">>>>>>>>>>>>>>>>>>BiddingService(2).is4xxClientError: {} {}", paths, response.getStatusCode());
            }
        } catch (HttpClientErrorException exp) {
            // try to callout again when error??
            this.revokeAccessToken();
            response = callout(paths, method, requestBody);
            log.info(">>>>>>>>>>>>>>>>>>BiddingService(3).HttpClientErrorException: {} {}", paths, response.getStatusCode());
            log.info(">>>>>>>>>>>>>>>>>>BiddingService.Exception: {} ------------------>>>", exp.getClass());
            log.info(">>>>>>>>>>>>>>>>>>BiddingService.Exception: {}, Trace: {}", exp.getMessage(), exp.getStackTrace());
        }
        return response;
    }

    private ResponseEntity<?> callout(String paths, HttpMethod method, Object requestBody) {
        HttpEntity<?> requestEntity;
        ResponseEntity<?> response;
        if (Objects.nonNull(requestBody)) {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            requestEntity = new HttpEntity<>(requestBody, httpHeaders);
        } else requestEntity = new HttpEntity<>(httpHeaders);
        response = restTemplate.exchange(baseUrl + paths, method, requestEntity, Object.class);
        return response;
    }

}
