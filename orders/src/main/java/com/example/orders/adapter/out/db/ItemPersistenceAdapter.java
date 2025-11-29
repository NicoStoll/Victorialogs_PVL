package com.example.orders.adapter.out.db;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.orders.application.domain.model.Item;
import com.example.orders.application.port.out.CreateItemPort;
import com.example.orders.application.port.out.DeleteItemPort;
import com.example.orders.application.port.out.GetItemByIdPort;
import com.example.orders.application.port.out.GetItemsPort;
import com.example.orders.application.port.out.UpdateItemPort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItemPersistenceAdapter implements CreateItemPort, GetItemsPort, DeleteItemPort, GetItemByIdPort, UpdateItemPort {


    private ItemRepository itemRepository;

    @Override
    public Item createItem(Item item) {
        
        ItemEntity entity = new ItemEntity();
        entity.setName(item.getName());

        ItemEntity result = itemRepository.save(entity);

        return result.toItem();
    }

    @Override
    public List<Item> getItems() {
        
        return this.itemRepository.findAll()
            .stream()
            .map(i -> i.toItem())
            .toList();
    }

    @Override
    public void deleteItem(Long id) {

        this.itemRepository.deleteById(id);
    }

    @Override
    public Optional<Item> getItemById(Long id) {
        
        return itemRepository.findById(id)
            .map(entity -> Optional.of(entity.toItem()))
            .orElse(Optional.empty());
    }

    @Override
    public Item updateItem(Long id, Item updated) {
        
        ItemEntity entity = new ItemEntity(updated.getId(), updated.getName());
        
        itemRepository.save(entity);

        return entity.toItem();

    }


}
