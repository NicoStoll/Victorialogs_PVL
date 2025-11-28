package com.example.orders.application.port.out;

import java.util.Optional;

import com.example.orders.application.domain.model.Item;

public interface GetItemByIdPort {

    Optional<Item> getItemById(Long id);
    
}
