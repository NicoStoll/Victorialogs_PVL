package com.example.orders.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteItemCommand {

    private Long id;
}
