package com.vireak.bidding.repository;

import com.vireak.bidding.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findAllByCreatedBy(Long createdBy);
}
