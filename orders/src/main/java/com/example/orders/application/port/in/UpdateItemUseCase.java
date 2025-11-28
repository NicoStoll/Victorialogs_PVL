package com.example.orders.application.port.in;

import com.example.orders.application.domain.model.Item;

public interface UpdateItemUseCase {

    Item updateItem(UpdateItemCommand updateItemCommand);
}
