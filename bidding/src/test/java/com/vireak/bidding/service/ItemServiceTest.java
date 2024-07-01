package com.vireak.bidding.service;

import com.vireak.bidding.entity.Bid;
import com.vireak.bidding.entity.Item;
import com.vireak.bidding.entity.Session;
import com.vireak.bidding.model.BidResponse;
import com.vireak.bidding.model.ItemRequest;
import com.vireak.bidding.repository.ItemRepository;
import com.vireak.bidding.utils.ApplicationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    ItemRepository itemRepository;
    @Mock
    ApplicationUtils applicationUtils;

    @InjectMocks
    ItemService itemService;

    ItemRequest itemRequest;

    @BeforeEach
    void setUp() {
        itemRequest = ItemRequest.builder()
                .name("Test")
                .price(100D)
                .build();
    }

    @Test
    void findAll() {
        when(itemRepository.findAll()).thenReturn(List.of(
                Item.builder().name(itemRequest.getName()).build()
        ));
        assertEquals(1, itemService.findAll().size());
    }

    @Test
    void save() {
        Item item0 = Item.builder()
                .price(itemRequest.getPrice())
                .build();
        when(itemRepository.save(any(Item.class))).thenReturn(item0);
        Item item1 = itemService.save(itemRequest);
        assertEquals(item0.getPrice(),item1.getPrice());
    }

    @Test
    void findByIdRequired() {
        // Act and Assert
        assertThrows(RuntimeException.class, () -> {
            itemService.findByIdRequired(0L);
        });
    }

    @Test
    void findAllBids() {
        Item saveditem=Item.builder()
                .id(1L)
                .name("Test")
                .price(100D)
                .sessions(
                       List.of(
                               Session.builder()
                                       .bids(
                                               List.of(
                                                       Bid.builder()
                                                               .amount(100D)
                                                               .bidTime(LocalDateTime.now(ZoneOffset.UTC))
                                                               .build()
                                               )
                                       )
                                       .build()
                       )
                )
                .build();
        when(itemRepository.findById(any(Long.class)))
                .thenReturn(Optional.ofNullable(saveditem));
        assert saveditem != null;
        List<BidResponse> bidResponses=itemService.findAllBids(saveditem.getId());
        assertEquals(1,bidResponses.size());
    }
}