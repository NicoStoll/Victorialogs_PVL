package com.example.orders.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.orders.application.domain.model.Item;
import com.example.orders.application.port.in.CreateItemCommand;
import com.example.orders.application.port.in.CreateItemUseCase;
import com.example.orders.application.port.in.GetItemsUseCase;
import com.example.orders.application.port.out.CreateItemPort;
import com.example.orders.application.port.out.GetItemsPort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItemService implements CreateItemUseCase, GetItemsUseCase{

    private CreateItemPort createItemPort;

    private GetItemsPort getItemsPort;

    @Override
    public Item createItem(CreateItemCommand command) {

        return this.createItemPort.createItem(new Item(-1, command.getName()));
    }

    @Override
    public List<Item> getAllItems() {
        
        return this.getItemsPort.getItems();
    }

}
