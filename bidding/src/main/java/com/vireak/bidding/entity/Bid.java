package com.vireak.bidding.entity;

import com.vireak.bidding.enums.BidStatus;
import com.vireak.bidding.model.BidHistoryResponse;
import com.vireak.bidding.model.BidResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

import static com.vireak.bidding.enums.BidStatus.PENDING;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Bid extends EntityBase {

    @Column(nullable = false)
    private Double amount;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime bidTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BidStatus status = PENDING;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;*/

    public BidResponse toResponse() {
        return BidResponse.builder()
                .id(super.getId())
                .amount(this.amount)
                .status(this.status)
                .comment(this.comment)
                .bidTime(this.bidTime)
                .build();
    }

    public BidHistoryResponse toHistoryResponse() {
        return BidHistoryResponse.builder()
                .bid(toResponse())
                .session(session.toResponse())
                .build();
    }
}
