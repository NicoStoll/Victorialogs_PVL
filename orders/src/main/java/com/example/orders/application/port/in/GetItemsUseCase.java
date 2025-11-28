package com.example.orders.application.port.in;

import java.util.List;

import com.example.orders.application.domain.model.Item;

public interface GetItemsUseCase {

    public List<Item> getAllItems();

}
