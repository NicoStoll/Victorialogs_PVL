package com.example.orders.application.port.out;

import java.util.List;

import com.example.orders.application.domain.model.Item;

public interface GetItemsPort {

    List<Item> getItems();
}
