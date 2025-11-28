package com.example.orders.application.port.out;

import com.example.orders.application.domain.model.Item;

public interface UpdateItemPort {

    Item updateItem(Long id, Item updated);
}
