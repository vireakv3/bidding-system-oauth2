package com.vireak.bidding.repository;

import com.vireak.bidding.entity.Bid;
import com.vireak.bidding.enums.BidStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid,Long> {
    List<Bid> findAllByCreatedBy(Long userId);
    List<Bid> findAllByCreatedByAndStatus(Long createdBy, BidStatus status);
    List<Bid> findAllByCreatedByAndStatusNot(Long createdBy, BidStatus status);
}
