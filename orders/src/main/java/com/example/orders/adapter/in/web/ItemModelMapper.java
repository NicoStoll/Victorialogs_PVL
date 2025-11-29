package com.example.orders.adapter.in.web;

import com.example.orders.application.domain.model.Item;


public class ItemModelMapper {

    ItemModel toItemModel(Item item) {
        
        return new ItemModel(item.getId(), item.getName(), item.getCreatedAt(), item.getUpdatedAt());
    }
    
}
