package com.vireak.bidding.controller;

import com.vireak.bidding.entity.User;
import com.vireak.bidding.model.ItemRequest;
import com.vireak.bidding.service.BidService;
import com.vireak.bidding.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    final private ItemService itemService;

    // retrieve all items belong to current login user
    @GetMapping
    public ResponseEntity<?> findAll(@AuthenticationPrincipal User userPrincipal) {
        if(Objects.isNull(userPrincipal))throw new RuntimeException("Not connected User!");
        return ResponseEntity.ok(itemService.findAll(userPrincipal.getId()));
    }

    // retrieve all items belong specify user Id
    @GetMapping("/{itemId}/bids")
    public ResponseEntity<?> findAllBids(@PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.findAllBids(itemId));
    }

    // retrieve all items belong specify user Id
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findAllByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(itemService.findAll(userId));
    }

    // sing item saving
    @PostMapping()
    public ResponseEntity<?> save(@Validated @RequestBody ItemRequest itemRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(itemService.save(itemRequest));
    }
}
