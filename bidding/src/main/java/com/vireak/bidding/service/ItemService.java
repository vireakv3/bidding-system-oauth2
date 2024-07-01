package com.vireak.bidding.service;

import com.vireak.bidding.entity.Bid;
import com.vireak.bidding.entity.Item;
import com.vireak.bidding.model.BidResponse;
import com.vireak.bidding.model.ItemRequest;
import com.vireak.bidding.model.ItemResponse;
import com.vireak.bidding.repository.ItemRepository;
import com.vireak.bidding.utils.ApplicationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    final private ItemRepository itemRepository;
    final private ApplicationUtils applicationUtils;

    public List<ItemResponse> findAll() {
        return toResponse(itemRepository.findAll());
    }

    public List<ItemResponse> findAll(Long userId) {
        return toResponse(itemRepository.findAllByCreatedBy(userId));
    }

    private List<ItemResponse> toResponse(List<Item> items) {
        return items.stream()
                .map(Item::toResponse)
                .toList();
    }

    public Item save(ItemRequest itemRequest) {
        // TODO: Validate Item before save
        Item item = Item.builder()
                .name(itemRequest.getName())
                .price(itemRequest.getPrice())
                .build();
        if(applicationUtils.isConnect())item.setCreatedBy(applicationUtils.getConnectedUser().getId());
        return itemRepository.save(item);
    }

    public Item findByIdRequired(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(()
                        -> new RuntimeException("Item with id=" + itemId + "not found!"));
    }

    public List<BidResponse> findAllBids(Long itemId) {
        var item = findByIdRequired(itemId);
        var sessions = item.getSessions();
        List<Bid> bids = new ArrayList<>();
        sessions.forEach(session -> {
            bids.addAll(session.getBids());
        });
        //return bidService.toBidHistoryResponses(bids);
        return bids.stream()
                .map(Bid::toResponse)
                .toList();
    }
}
