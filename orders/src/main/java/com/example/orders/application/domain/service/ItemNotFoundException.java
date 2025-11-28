package com.example.orders.application.domain.service;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
