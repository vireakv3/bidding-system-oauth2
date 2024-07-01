package com.vireak.bidding.model;

import com.vireak.bidding.enums.SessionStatus;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SessionRequest {
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private SessionStatus status;
    private Long itemId;
    private Long durationMinute = 2L;
}
