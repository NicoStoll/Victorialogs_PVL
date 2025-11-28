package com.example.orders.adapter.out.db;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.orders.application.domain.model.Item;
import com.example.orders.application.port.out.CreateItemPort;
import com.example.orders.application.port.out.GetItemsPort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItemPersistenceAdapter implements CreateItemPort, GetItemsPort{


    private ItemRepository itemRepository;

    @Override
    public Item createItem(Item item) {
        
        ItemEntity entity = new ItemEntity();
        entity.setName(item.getName());

        ItemEntity result = itemRepository.save(entity);

        return new Item(result.getId(), result.getName());
    }

    @Override
    public List<Item> getItems() {
        
        return this.itemRepository.findAll()
            .stream()
            .map(i -> new Item(i.getId(), i.getName()))
            .toList();
    }

}
