package com.vireak.bidding.controller;

import com.vireak.bidding.model.BidRequest;
import com.vireak.bidding.model.SessionRequest;
import com.vireak.bidding.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
public class SessionController {

    final private SessionService sessionService;

    // get all session active sessions
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(sessionService.findAllActive());
    }

    // get active valid session
    // a. Join the bid session
    @GetMapping("/{sessionId}")
    public ResponseEntity<?> findById(@PathVariable("sessionId") Long sessionId) {
        return ResponseEntity.ok(sessionService.findByIdRequiredResponse(sessionId));
    }

    // get bidder highest price
    @GetMapping("/{sessionId}/top-bidder")
    public ResponseEntity<?> findAllTopBidder(@PathVariable Long sessionId) {
        return ResponseEntity.ok(sessionService.findAllTopBidder(sessionId));
    }

    // single session saving
    @PostMapping
    public ResponseEntity<?> save(@Validated @RequestBody SessionRequest dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sessionService.save(dto));
    }

    // update session
    @PatchMapping("/{sessionId}")
    public ResponseEntity<?> closeSession(
            @RequestBody SessionRequest dto,
            @PathVariable("sessionId") Long sessionId) {
        return ResponseEntity.ok(sessionService.partialSave(sessionId,dto));
    }

    // post single bid saving
    // b. post new bid price
    @PostMapping("/{sessionId}/bid")
    public ResponseEntity<?> postBid(
            @PathVariable("sessionId") Long sessionId,
            @Validated @RequestBody BidRequest dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sessionService.postBid(sessionId,dto));
    }
}
