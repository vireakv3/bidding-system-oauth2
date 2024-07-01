package com.vireak.bidding.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vireak.bidding.enums.SessionStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SessionResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private Long durationMinute;
    private SessionStatus status;
    private ItemResponse item;
    private BidResponse topBid;
    private List<BidResponse> bids;
}