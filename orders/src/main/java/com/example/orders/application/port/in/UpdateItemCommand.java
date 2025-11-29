package com.example.orders.application.port.in;

import com.example.orders.application.domain.model.Item;

import lombok.Getter;
import lombok.extern.java.Log;

@Log
@Getter
public class UpdateItemCommand {

    private final Long id;

    private final Item updated;

    public UpdateItemCommand(final Long id, final Item updated) throws ItemUpdateValidationException {

        if(id != updated.getId()) {

            log.warning("Validation of update item failed");
            
            throw new ItemUpdateValidationException("Item Id does not match the update id");
        }

        this.id = id;
        this.updated = updated;
    }
}
