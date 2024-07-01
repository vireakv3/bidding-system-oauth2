package com.vireak.bidding.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vireak.bidding.enums.SessionStatus;
import com.vireak.bidding.model.SessionResponse;
import com.vireak.bidding.utils.ApplicationUtils;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.vireak.bidding.enums.BidStatus.TOP;
import static com.vireak.bidding.enums.SessionStatus.ACTIVE;
import static com.vireak.bidding.enums.SessionStatus.CLOSED;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Session extends EntityBase {

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime startTime;
    private Long durationMinute;

    //@Enumerated(EnumType.STRING)
    //@Column(nullable = false)
    @Transient
    private SessionStatus status = ACTIVE;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @JsonManagedReference
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<Bid> bids = new ArrayList<>();

    public SessionStatus getStatus() {
        if (this.startTime != null && ApplicationUtils
                .nowUTC()
                .isAfter(this.startTime.plusMinutes(this.durationMinute))) {
            this.status = CLOSED;
        } else this.status = ACTIVE;
        return this.status;
    }

    public Bid getTopBid() {
        if (Objects.isNull(bids)) return null;
        return this.bids.stream()
                .filter(bid -> TOP.equals(bid.getStatus()))
                .findFirst()
                .orElse(null);
    }

    public SessionResponse toResponse(@Nonnull Boolean includeBids) {
        var sessionResponse = SessionResponse.builder()
                .id(super.getId())
                .description(this.description)
                .title(this.title)
                .startTime(this.startTime)
                .durationMinute(this.durationMinute)
                .status(this.getStatus())
                .build();
        if (Objects.nonNull(getTopBid())) sessionResponse.setTopBid(getTopBid().toResponse());
        if (Objects.nonNull(this.item)) sessionResponse.setItem(this.item.toResponse());
        if (includeBids && Objects.nonNull(this.bids)) {
            sessionResponse.setBids(
                    this.bids.stream()
                            .sorted(Comparator.comparing(Bid::getBidTime).reversed())
                            .map(Bid::toResponse)
                            .toList()
            );
        }
        return sessionResponse;
    }

    public SessionResponse toResponse() {
        return toResponse(false);
    }
}
