package com.vireak.bidding.repository;

import com.vireak.bidding.entity.Session;
import com.vireak.bidding.enums.SessionStatus;
import com.vireak.bidding.utils.ApplicationUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    default List<Session> findAllActive(){
        List<Session> allSessions = findAll();
        return allSessions.stream()
                .filter(session ->
                        session.getStartTime()
                                .plusMinutes(session.getDurationMinute())
                                .isAfter(ApplicationUtils.nowUTC()))
                .toList();
    }
}
