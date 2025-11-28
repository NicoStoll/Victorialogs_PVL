package com.example.orders.adapter.in.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.orders.application.domain.model.Item;
import com.example.orders.application.port.in.CreateItemCommand;
import com.example.orders.application.port.in.CreateItemUseCase;
import com.example.orders.application.port.in.GetItemsUseCase;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Log
@RestController
@AllArgsConstructor
public class ItemController {

    private final CreateItemUseCase createItemUseCase;

    private final GetItemsUseCase getItemsUseCase;

    @PostMapping("items")
    public ResponseEntity<EntityModel<ItemModel>> createItem(@RequestBody CreateItemDTO entity) {

        log.info("Endpoint for creation of new Item has been called");
        
        CreateItemCommand command = new CreateItemCommand(entity.getName());
        Item item = createItemUseCase.createItem(command);

        ItemModel model = new ItemModel(item.getId(), item.getName());

        EntityModel<ItemModel> resource = EntityModel.of(
                model,
                linkTo(methodOn(ItemController.class).getItemById(item.getId())).withSelfRel(),
                linkTo(methodOn(ItemController.class).getAllItems()).withRel("items")
        );

        return ResponseEntity
                .created(
                    linkTo(methodOn(ItemController.class).getItemById(item.getId())).toUri()
                )
                .body(resource);
    }

    @GetMapping("items/{id}")
    public EntityModel<ItemModel> getItemById(@PathVariable Long id) {

        log.warning("THIS IS NOT IMPLEMENTED YET IN VERSION 0.0.1");

        // stub example
        ItemModel model = new ItemModel(id, "placeholder");
        return EntityModel.of(model);

    }

    @GetMapping("items")
    public CollectionModel<EntityModel<ItemModel>> getAllItems() {

        List<EntityModel<ItemModel>> itemModels = getItemsUseCase.getAllItems()
            .stream()
            .map(item -> {
                ItemModel model = new ItemModel(item.getId(), item.getName());

                // Wrap in EntityModel and add self link
                return EntityModel.of(model,
                    linkTo(methodOn(ItemController.class).getItemById(item.getId())).withSelfRel()
                );
            })
            .collect(Collectors.toList());

        // Wrap the list in a CollectionModel and add a link to the collection itself
        return CollectionModel.of(itemModels,
            linkTo(methodOn(ItemController.class).getAllItems()).withSelfRel()
        );

    }
}
