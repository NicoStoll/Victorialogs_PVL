package com.example.orders.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateItemCommand {

    private final String name;
}
