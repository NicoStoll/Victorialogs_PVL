package com.example.orders.adapter.in.web;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ItemModel extends RepresentationModel<ItemModel> {

    private long id;
    
    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
