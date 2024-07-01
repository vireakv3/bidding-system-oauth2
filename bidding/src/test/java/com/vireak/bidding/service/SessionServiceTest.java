package com.vireak.bidding.service;

import com.vireak.bidding.entity.Bid;
import com.vireak.bidding.entity.Item;
import com.vireak.bidding.entity.Session;
import com.vireak.bidding.entity.User;
import com.vireak.bidding.enums.BidStatus;
import com.vireak.bidding.enums.SessionStatus;
import com.vireak.bidding.model.BidRequest;
import com.vireak.bidding.model.SessionRequest;
import com.vireak.bidding.repository.BidRepository;
import com.vireak.bidding.repository.SessionRepository;
import com.vireak.bidding.utils.ApplicationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {

    @Mock
    SessionRepository sessionRepository;
    @Mock
    ItemService itemService;
    @Mock
    ApplicationUtils applicationUtils;
    @Mock
    BidRepository bidRepository;
    @Mock
    BidService bidService;
    @Mock
    UserService userService;

    @InjectMocks
    SessionService sessionService;

    SessionRequest sessionRequest;

    @BeforeEach
    void setUp() {
        sessionRequest = SessionRequest.builder()
                .title("Test")
                .description("OK")
                .durationMinute(2L)
                .startTime(LocalDateTime.now(ZoneOffset.UTC))
                .itemId(1L)
                .build();
    }

    @Test
    void save() {
        when(itemService.findByIdRequired(sessionRequest.getItemId()))
                .thenReturn(
                        Item.builder()
                                .id(sessionRequest.getItemId())
                                .build());
        when(sessionRepository.save(any(Session.class)))
                .thenReturn(Session.builder()
                        .title(sessionRequest.getTitle())
                        .build());
        var sessionResponse = sessionService.save(sessionRequest);
        assertEquals(sessionRequest.getTitle(), sessionResponse.getTitle());
    }

    @Test
    void partialSave() {
        Session session = Session.builder()
                .title(sessionRequest.getTitle())
                .build();
        when(sessionRepository.findById(any(Long.class)))
                .thenReturn(Optional.ofNullable(session));
        when(sessionRepository.save(any(Session.class)))
                .thenReturn(session);
        var sessionResponse = sessionService.partialSave(1L, sessionRequest);
        assertEquals(session.getTitle(), sessionResponse.getTitle());
    }

    @Test
    void postBid() {
        Session session = Session.builder()
                .title(sessionRequest.getTitle())
                .build();
        when(sessionRepository.findById(any(Long.class)))
                .thenReturn(Optional.ofNullable(session));
        when(sessionRepository.save(any(Session.class)))
                .thenReturn(session);
        var bidReq = BidRequest.builder()
                .comment("Test")
                .amount(100D)
                .build();
        when(bidService.save(session, bidReq))
                .thenReturn(Bid.builder().amount(bidReq.getAmount()).build());
        var sessionResponse = sessionService.postBid(1L, bidReq);
        assertEquals(sessionResponse.getAmount(), bidReq.getAmount());
    }

    @Test
    void findAll() {
        Session session = Session.builder()
                .title(sessionRequest.getTitle())
                .build();
        when(sessionRepository.findAll())
                .thenReturn(List.of(session));
        var res = sessionService.findAll();
        assertEquals(1, res.size());
    }

    @Test
    void findAllActive() {
        Session session = Session.builder()
                .title(sessionRequest.getTitle())
                .build();
        when(sessionRepository.findAllActive())
                .thenReturn(List.of(session));
        var res = sessionService.findAllActive();
        assertEquals(1, res.size());
    }

    @Test
    void findByIdRequiredActive() {
        when(sessionRepository.findById(any(Long.class)))
                .thenReturn(Optional.ofNullable(
                        Session.builder()
                                .id(1L)
                                .status(SessionStatus.ACTIVE)
                                .build()));
        var res = sessionService.findByIdRequiredActive(1L);
        assertEquals(SessionStatus.ACTIVE, res.getStatus());
    }

    @Test
    void findAllTopBidder() {
        when(sessionRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(
                        Session.builder()
                                .id(1L)
                                .title("Test")
                                .bids(
                                        List.of(
                                                Bid.builder()
                                                        .status(BidStatus.TOP)
                                                        .createdBy(1L)
                                                        .build()
                                        )
                                )
                                .build()));
        when(userService.findById(any(Long.class)))
                .thenReturn(User.builder()
                        .email("TestTOpUser@yahoo")
                        .build());
        var res = sessionService.findAllTopBidder(1L);
        assertEquals("TestTOpUser@yahoo", res.getUser().getEmail());
    }
}