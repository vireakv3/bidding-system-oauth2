package com.vireak.bidding.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vireak.bidding.entity.Item;
import com.vireak.bidding.entity.Session;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ItemResponse {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private List<SessionResponse> sessions;
}
