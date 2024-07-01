package com.vireak.bidding.service;

import com.vireak.bidding.entity.Bid;
import com.vireak.bidding.entity.Session;
import com.vireak.bidding.enums.SessionStatus;
import com.vireak.bidding.model.BidHistoryResponse;
import com.vireak.bidding.model.BidRequest;
import com.vireak.bidding.model.BidResponse;
import com.vireak.bidding.repository.BidRepository;
import com.vireak.bidding.utils.ApplicationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.vireak.bidding.enums.BidStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BidService {

    final private BidRepository bidRepository;
    final private ApplicationUtils applicationUtils;

    public List<Bid> saveAll(List<Bid> bids) {
        return bidRepository.saveAll(bids);
    }

    public Bid save(Session session, BidRequest dto) {
        var bid = Bid.builder()
                .amount(dto.getAmount())
                .comment(dto.getComment())
                .bidTime(LocalDateTime.now())
                .session(session)
                .build();
        if (applicationUtils.isConnect()) bid.setCreatedBy(applicationUtils.getConnectedUser().getId());
        var topBid = session.getTopBid();
        List<Bid> bids = new ArrayList<>();
        bids.add(bid);
        // invalid price
        if (bid.getAmount() < session.getItem().getPrice()) {
            bid.setStatus(REJECTED);
            bid.setComment("Bid rejected due the amount lower then price!");
        }
        // currently win
        else if (
            // has top bidder
                Objects.nonNull(topBid) && topBid.getAmount() < bid.getAmount()
                        // not bidder yet, then first bidder
                        || Objects.isNull(topBid)
        ) {
            if (Objects.nonNull(topBid)) {
                topBid.setStatus(ACCEPTED);
                bids.add(topBid);
            }
            bid.setStatus(TOP);
        }
        // currently lost
        else bid.setStatus(ACCEPTED);
        bidRepository.saveAll(bids);
        log.info(">BidService.save: {}", bids);
        return bid;
    }

    public List<BidResponse> findAll() {
        return bidRepository.findAll()
                .stream()
                .map(Bid::toResponse)
                .toList();
    }

    public List<BidHistoryResponse> findAll(Long userId) {
        return toBidHistoryResponses(bidRepository.findAllByCreatedBy(userId));
    }

    public List<BidHistoryResponse> toBidHistoryResponses(List<Bid> bids) {
        return bids.stream()
                .map(Bid::toHistoryResponse)
                .toList();
    }

    public List<BidHistoryResponse> findAllWin(Long userId) {
        return toBidHistoryResponses(
                filterClosedSession(
                        bidRepository.findAllByCreatedByAndStatus(userId, TOP))
        );
    }

    public List<BidHistoryResponse> findAllLost(Long userId) {
        return toBidHistoryResponses(
                filterClosedSession(
                        bidRepository.findAllByCreatedByAndStatusNot(userId, TOP))
        );
    }

    private List<Bid> filterClosedSession(List<Bid> bids) {
        return bids
                .stream()
                .filter(bid -> SessionStatus.CLOSED.equals(bid.getSession().getStatus()))
                .collect(Collectors.toList());
    }
}
