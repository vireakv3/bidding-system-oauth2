package com.vireak.bidding.service;

import com.vireak.bidding.entity.Session;
import com.vireak.bidding.entity.User;
import com.vireak.bidding.model.*;
import com.vireak.bidding.repository.SessionRepository;
import com.vireak.bidding.utils.ApplicationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.vireak.bidding.enums.SessionStatus.CLOSED;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    final private SessionRepository sessionRepository;
    final private ItemService itemService;
    final private BidService bidService;
    final private UserService userService;
    final private ApplicationUtils applicationUtils;

    public SessionResponse save(SessionRequest dto) {
        // TODO: valid any additional logic before save
        if (Objects.isNull(dto.getItemId())) throw new RuntimeException("Missing itemId to create session!");
        var item = itemService.findByIdRequired(dto.getItemId());
        Session session = Session.builder()
                .title(dto.getTitle())
                .status(dto.getStatus())
                .description(dto.getDescription())
                .startTime(dto.getStartTime())
                .durationMinute(dto.getDurationMinute())
                .item(item)
                .build();
        if(applicationUtils.isConnect())session.setCreatedBy(applicationUtils.getConnectedUser().getId());
        return sessionRepository.save(session)
                .toResponse();
    }

    public SessionResponse partialSave(Long sessionId, SessionRequest dto) {
        Session session = findByIdRequired(sessionId);
        if (Strings.isNotBlank(dto.getTitle())) session.setTitle(dto.getTitle());
        if (Strings.isNotBlank(dto.getDescription())) session.setDescription(dto.getDescription());
        if (Objects.nonNull(dto.getStatus())) session.setStatus(dto.getStatus());
        if (Objects.nonNull(dto.getStartTime())) session.setStartTime(dto.getStartTime());
        if (Objects.nonNull(dto.getDurationMinute())) session.setDurationMinute(dto.getDurationMinute());
        if (Objects.nonNull(dto.getItemId())) {
            var item = itemService.findByIdRequired(dto.getItemId());
            session.setItem(item);
        }
        return sessionRepository.save(session)
                .toResponse();
    }

    public BidResponse postBid(Long sessionId, BidRequest dto) {
        var session = findByIdRequiredActive(sessionId);
        // PENDING Bid
        var bid = bidService.save(session, dto);
        if (Objects.isNull(session.getBids())) session.setBids(new ArrayList<>());
        session.getBids().add(bid);
        sessionRepository.save(session);
        return bid.toResponse();
    }

    public List<SessionResponse> findAll() {
        return toResponses(sessionRepository.findAll());
    }

    public List<SessionResponse> findAllActive() {
        return toResponses(sessionRepository.findAllActive());
    }

    private List<SessionResponse> toResponses(List<Session> sessions) {
        return sessions.stream()
                .map(Session::toResponse)
                .toList();
    }

    public Session findByIdRequiredActive(Long sessionId) {
        var session = findByIdRequired(sessionId);
        if (CLOSED.equals(session.getStatus())) {
            throw new RuntimeException("Session with Id=" + sessionId + " is closed!");
        }
        return session;
    }

    public Session findByIdRequired(Long sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException(
                        "Session with Id=" + sessionId + " not found!"));
    }

    public SessionResponse findByIdRequiredResponse(Long sessionId) {
        return findByIdRequired(sessionId).toResponse(true);
    }

    public TopBidderResponse findAllTopBidder(Long sessionId) {
        var topBidderResponse = TopBidderResponse.builder().build();
        var session = findByIdRequired(sessionId);
        var topBid = session.getTopBid();
        if (Objects.nonNull(topBid)) {
            var user = userService.findById(topBid.getCreatedBy());
            topBidderResponse.setTopBid(topBid.toResponse());
            topBidderResponse.setUser(user.toResponse());
        }
        return topBidderResponse;
    }
}
