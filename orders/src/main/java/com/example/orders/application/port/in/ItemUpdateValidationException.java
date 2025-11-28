package com.example.orders.application.port.in;

public class ItemUpdateValidationException extends Exception {

    public ItemUpdateValidationException(String errorMessage) {
        super(errorMessage);
    }
}
