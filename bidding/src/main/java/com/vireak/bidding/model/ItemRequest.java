package com.vireak.bidding.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemRequest {
    private String name;
    private Double price;
}
