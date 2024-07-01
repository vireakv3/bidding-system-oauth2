package com.vireak.bidding.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BidRequest {
    private Double amount;
    private String comment;
}
