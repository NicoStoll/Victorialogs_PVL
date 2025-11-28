package com.example.orders.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.orders.application.domain.service.ItemNotFoundException;
import com.example.orders.application.port.in.ItemUpdateValidationException;

import lombok.extern.java.Log;

@Log
@ControllerAdvice
public class ItemEndpointExceptionHandler {

    @ExceptionHandler(ItemUpdateValidationException.class)
    public ResponseEntity<Void> handleItemUpdateValidationError(ItemUpdateValidationException itemUpdateValidationException) {

        log.warning("There has been a mismatch between the id in the url and the request body of object item");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Void> handleItemNotFoundException(ItemNotFoundException updaItemNotFoundException) {

        log.warning("The requested item cannot be found");
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
