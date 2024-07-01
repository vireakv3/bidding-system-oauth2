package com.vireak.bidding.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BidHistoryResponse {
    private BidResponse bid;
    private SessionResponse session;
}
