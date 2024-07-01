package com.vireak.bidding.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vireak.bidding.entity.Session;
import com.vireak.bidding.enums.BidStatus;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BidResponse {
    private Long id;
    private Double amount;
    private BidStatus status;
    private String comment;
    private LocalDateTime bidTime;
}
