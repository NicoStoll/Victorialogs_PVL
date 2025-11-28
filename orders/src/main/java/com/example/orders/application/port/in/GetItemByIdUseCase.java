package com.example.orders.application.port.in;

import java.util.Optional;

import com.example.orders.application.domain.model.Item;

public interface GetItemByIdUseCase {

    Optional<Item> getItemById(GetItemByIdCommand command);
}
