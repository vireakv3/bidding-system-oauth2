package com.vireak.bidding.controller;

import com.vireak.bidding.entity.User;
import com.vireak.bidding.service.BidService;
import com.vireak.bidding.utils.ApplicationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/bids")
@RequiredArgsConstructor
public class BidController {

    final private BidService bidService;
    final private ApplicationUtils applicationUtils;

    private User getConnectedUser() {
        var user = applicationUtils.getConnectedUser();
        if (Objects.isNull(user)) throw new RuntimeException("Not connected User!");
        return (User) user;
    }

    // c. see his own bid history
    @GetMapping
    public ResponseEntity<?> findBelongConnectedUser() {
        return ResponseEntity
                .ok(bidService.findAll(getConnectedUser().getId()));
    }

    // d. see win history
    @GetMapping("win")
    public ResponseEntity<?> findBelongWinConnectedUser() {
        return win(getConnectedUser().getId());
    }

    // e. see lost history
    @GetMapping("lost")
    public ResponseEntity<?> findBelongLostConnectedUser() {
        return lost(getConnectedUser().getId());
    }

    // view from client
    @GetMapping("/{userId}/win")
    public ResponseEntity<?> findWinSpecifyId(@PathVariable Long userId) {
        return win(userId);
    }

    @GetMapping("/{userId}/lost")
    public ResponseEntity<?> findLostSpecifyId(@PathVariable Long userId) {
        return lost(userId);
    }

    private ResponseEntity<?> lost(Long userId) {
        return ResponseEntity
                .ok(bidService.findAllLost(userId));
    }

    private ResponseEntity<?> win(Long userId) {
        return ResponseEntity
                .ok(bidService.findAllWin(userId));
    }
}
