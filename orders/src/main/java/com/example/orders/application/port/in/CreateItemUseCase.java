package com.example.orders.application.port.in;

import com.example.orders.application.domain.model.Item;

public interface CreateItemUseCase {

    Item createItem(CreateItemCommand command);

}
