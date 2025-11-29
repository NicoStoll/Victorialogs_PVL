package com.example.orders.application.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.orders.application.domain.model.Item;
import com.example.orders.application.port.in.CreateItemCommand;
import com.example.orders.application.port.in.CreateItemUseCase;
import com.example.orders.application.port.in.DeleteItemCommand;
import com.example.orders.application.port.in.DeleteItemUseCase;
import com.example.orders.application.port.in.GetItemByIdCommand;
import com.example.orders.application.port.in.GetItemByIdUseCase;
import com.example.orders.application.port.in.GetItemsUseCase;
import com.example.orders.application.port.in.UpdateItemCommand;
import com.example.orders.application.port.in.UpdateItemUseCase;
import com.example.orders.application.port.out.CreateItemPort;
import com.example.orders.application.port.out.DeleteItemPort;
import com.example.orders.application.port.out.GetItemByIdPort;
import com.example.orders.application.port.out.GetItemsPort;
import com.example.orders.application.port.out.UpdateItemPort;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@AllArgsConstructor
public class ItemService implements CreateItemUseCase, GetItemsUseCase, GetItemByIdUseCase, DeleteItemUseCase, UpdateItemUseCase {

    private CreateItemPort createItemPort;

    private GetItemsPort getItemsPort;

    private DeleteItemPort deleteItemPort;

    private GetItemByIdPort getItemByIdPort;

    private UpdateItemPort updateItemPort;

    @Override
    public Item createItem(CreateItemCommand command) {

        return this.createItemPort.createItem(new Item(-1L, command.getName()));
    }

    @Override
    public List<Item> getAllItems() {
        
        return this.getItemsPort.getItems();
    }

    @Override
    public void deleteItem(DeleteItemCommand command) {
        
        Optional<Item> optional = this.getItemByIdPort.getItemById(command.getId());

        if(optional.isEmpty()) {

            log.warning("Item does not exist");

            return;
        }

        this.deleteItemPort.deleteItem(command.getId());
    }

    @Override
    public Item getItemById(GetItemByIdCommand command) {
        
        Optional<Item> optionalItem =  this.getItemByIdPort.getItemById(command.getId());

        if(optionalItem.isPresent()) {
            return optionalItem.get();
        }

        throw new ItemNotFoundException("Cannot GET item");
    }

    @Override
    public Item updateItem(UpdateItemCommand command) {

        Optional<Item> optional = this.getItemByIdPort.getItemById(command.getId());

        if(optional.isEmpty()) {

            throw new ItemNotFoundException("Update not possible since the item does not exist");
        }
        
        return this.updateItemPort.updateItem(command.getId(), command.getUpdated());

    }

}
