package com.vireak.client_server.controller;

import com.vireak.client_server.model.BidRequest;
import com.vireak.client_server.service.BiddingService;
import com.vireak.client_server.utils.ApplicationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final BiddingService biddingService;

    @GetMapping("/users")
    public String message(Principal principal) {
        ResponseEntity<?> response = biddingService.callout(principal, "/api/users", HttpMethod.GET);
        log.info(">>>users: {}", response.getBody());
        return "index";
    }

    private Object connectUser() {
        return ApplicationUtils.getConnectedUser();
    }

    private Boolean isCnn() {
        return ApplicationUtils.isConnect();
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/public";
    }

    @GetMapping("/public")
    public String home(Model model, Principal principal) {
        if (isCnn()) {
            ResponseEntity<?> response = biddingService.callout(principal, "/api/v1/sessions", HttpMethod.GET);
            log.info(">>>sessions: {}", response.getBody());
            List<Map<String, Object>> sessions = (List<Map<String, Object>>) response.getBody();
            model.addAttribute("sessions", sessions);
            model.addAttribute("user", connectUser());
            return "index";
        }
        return "redirect:/login";
    }


    @GetMapping("/public/join-session/{sessionId}")
    public String joinSession(Model model, Principal principal, @PathVariable Long sessionId) {
        model.addAttribute("user", connectUser());
        ResponseEntity<?> response = biddingService.callout(principal, "/api/v1/sessions/" + sessionId, HttpMethod.GET);
        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> session = (Map<String, Object>) response.getBody();
            log.info("joinSession:/public/join-session/{} >response: {}", sessionId, session);
            model.addAttribute("session", session);
            ResponseEntity<?> topBid = biddingService.callout(principal, "/api/v1/sessions/" + sessionId + "/top-bidder", HttpMethod.GET);
            Map<String, Object> topBidder = (Map<String, Object>) topBid.getBody();
            log.info("joinSession:/public/join-session/{}/top-bidde >response: {}", sessionId, topBidder);
            model.addAttribute("topBidder", topBidder);
        } else return "redirect:/public/error";
        return "session";
    }

    @PostMapping("/public/join-session/{sessionId}/post-bid")
    public String postBid(
            Model model,
            Principal principal,
            @PathVariable Long sessionId,
            @RequestParam("bidAmount") Double bidAmount) {
        BidRequest dto = BidRequest.builder()
                .amount(bidAmount)
                .build();
        ResponseEntity<?> response = biddingService.callout(
                principal,
                "/api/v1/sessions/" + sessionId + "/bid",
                HttpMethod.POST,
                dto);
        log.info("postBid:/public/join-session/{}/post-bid >response: {}", sessionId, response.getBody());
        if (response.getStatusCode().is2xxSuccessful()) {
            return "redirect:/public/join-session/" + sessionId;
        }
        return "redirect:/public/error";
    }

    @GetMapping("/public/win")
    public String win(Model model, Principal principal) {
        model.addAttribute("title", "Bids Win History");
        model.addAttribute("user", connectUser());
        ResponseEntity<?> response = biddingService.callout(principal, "/api/v1/bids/win", HttpMethod.GET);
        if (response.getStatusCode().is2xxSuccessful()) {
            List<Map<String, Object>> records = (List<Map<String, Object>>) response.getBody();
            log.info("/api/v1/bids/win >response: {}", records);
            model.addAttribute("bidsHistoies", records);
        } else return "redirect:/public/error";
        return "history";
    }

    @GetMapping("/public/lost")
    public String lost(Model model, Principal principal) {
        model.addAttribute("title", "Bids Lost History");
        model.addAttribute("user", connectUser());
        ResponseEntity<?> response = biddingService.callout(principal, "/api/v1/bids/lost", HttpMethod.GET);
        if (response.getStatusCode().is2xxSuccessful()) {
            List<Map<String, Object>> records = (List<Map<String, Object>>) response.getBody();
            log.info("/api/v1/bids/win >response: {}", records);
            model.addAttribute("bidsHistoies", records);
        } else return "redirect:/public/error";
        return "history";
    }

    @GetMapping("/public/all-bids")
    public String allBids(Model model, Principal principal) {
        model.addAttribute("title", "All Bids");
        model.addAttribute("user", connectUser());
        ResponseEntity<?> response = biddingService.callout(principal, "/api/v1/bids", HttpMethod.GET);
        if (response.getStatusCode().is2xxSuccessful()) {
            List<Map<String, Object>> records = (List<Map<String, Object>>) response.getBody();
            log.info("/api/v1/bids/win >response: {}", records);
            model.addAttribute("bidsHistoies", records);
        } else return "redirect:/public/error";
        return "history";
    }

}