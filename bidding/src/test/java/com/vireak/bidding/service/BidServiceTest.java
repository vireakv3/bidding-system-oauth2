package com.vireak.bidding.service;

import com.vireak.bidding.entity.Bid;
import com.vireak.bidding.entity.Item;
import com.vireak.bidding.entity.Session;
import com.vireak.bidding.enums.BidStatus;
import com.vireak.bidding.model.BidHistoryResponse;
import com.vireak.bidding.model.BidRequest;
import com.vireak.bidding.model.BidResponse;
import com.vireak.bidding.repository.BidRepository;
import com.vireak.bidding.utils.ApplicationUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BidServiceTest {

    @Mock
    private BidRepository bidRepository;

    @InjectMocks
    private BidService bidService;

    @Mock
    private ApplicationUtils applicationUtils;

    private Session session;
    private Item item;
    private Bid bid;

    @BeforeEach
    void setUp() {
        LocalDateTime nowUTC = LocalDateTime.now(ZoneOffset.UTC);
        this.item = Item.builder()
                .description("TestDescription")
                .name("TestItemName")
                .price(100D)
                .createdBy(1L)
                .build();
        this.session = Session.builder()
                .createdBy(1L)
                .title("TestSession")
                .description("TestDescription")
                .durationMinute(2L)
                .item(item)
                .startTime(nowUTC)
                .build();
        this.bid = Bid.builder()
                .session(session)
                .bidTime(nowUTC)
                .comment("")
                .status(BidStatus.ACCEPTED)
                .amount(100D)
                .build();
    }

    @Test
    void save() {
        BidRequest bidRequest = BidRequest.builder()
                .amount(100D)
                .comment("Test")
                .build();
        Bid bid = bidService.save(this.session, bidRequest);
        assertEquals(bid.getAmount(), bidRequest.getAmount());
    }

    @Test
    void findAll() {
        List<BidResponse> bids = new ArrayList<>();
        when(bidService.findAll()).thenReturn(bids);
        List<BidResponse> bidsResponse = bidService.findAll();
        assertEquals(bids, bidsResponse);
    }

    @Test
    void toBidHistoryResponses() {
        List<BidHistoryResponse> bidHistoryResponses = bidService
                .toBidHistoryResponses(List.of(bid));
        for (BidHistoryResponse bidHistoryResponse : bidHistoryResponses) {
            assertEquals(
                    bidHistoryResponse.getBid().getAmount(),
                    bid.getAmount());
        }
    }
}