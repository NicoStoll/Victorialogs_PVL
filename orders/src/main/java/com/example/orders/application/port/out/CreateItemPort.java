package com.example.orders.application.port.out;

import com.example.orders.application.domain.model.Item;

public interface CreateItemPort {

    Item createItem(Item item);
}
