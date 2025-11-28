package com.example.orders.adapter.in.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.orders.application.domain.model.Item;
import com.example.orders.application.port.in.CreateItemCommand;
import com.example.orders.application.port.in.CreateItemUseCase;
import com.example.orders.application.port.in.DeleteItemCommand;
import com.example.orders.application.port.in.DeleteItemUseCase;
import com.example.orders.application.port.in.GetItemByIdCommand;
import com.example.orders.application.port.in.GetItemByIdUseCase;
import com.example.orders.application.port.in.GetItemsUseCase;
import com.example.orders.application.port.in.ItemUpdateValidationException;
import com.example.orders.application.port.in.UpdateItemCommand;
import com.example.orders.application.port.in.UpdateItemUseCase;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@Log
@RestController
@AllArgsConstructor
public class ItemController {

    private final CreateItemUseCase createItemUseCase;

    private final GetItemsUseCase getItemsUseCase;

    private final DeleteItemUseCase deleteItemUseCase;

    private final GetItemByIdUseCase getItemByIdUseCase;

    private final UpdateItemUseCase updateItemUseCase;

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

    @DeleteMapping("items/{id}")
    public ResponseEntity<EntityModel<Void>> deleteItem(@PathVariable Long id) {

        this.deleteItemUseCase.deleteItem(new DeleteItemCommand(id));

        return ResponseEntity.status(204).build();
    }

    @GetMapping("items/{id}")
    public ResponseEntity<EntityModel<ItemModel>> getItemById(@PathVariable Long id) {

        Optional<Item> optionalItem = this.getItemByIdUseCase.getItemById(new GetItemByIdCommand(id));


        Item item = optionalItem.get();
        ItemModel model = new ItemModel(item.getId(), item.getName());

        EntityModel<ItemModel> resource = EntityModel.of(model,
                linkTo(methodOn(ItemController.class).getItemById(id)).withSelfRel(),
                linkTo(methodOn(ItemController.class).getAllItems()).withRel("items")
        );

        return ResponseEntity.ok(resource); // 200 OK
        

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

    @PutMapping("items/{id}")
    public ResponseEntity<EntityModel<ItemModel>> putMethodName(@PathVariable Long id, @RequestBody UpdateItem entity) throws ItemUpdateValidationException {
        
        Item updated = new Item(entity.getId(), entity.getName());

        Item result = this.updateItemUseCase.updateItem(new UpdateItemCommand(id, updated));

        ItemModel model = new ItemModel(result.getId(), result.getName());

        EntityModel<ItemModel> resource = EntityModel.of(model,
                            linkTo(methodOn(ItemController.class).getItemById(id)).withSelfRel(),
                            linkTo(methodOn(ItemController.class).getAllItems()).withRel("items")
                    );

        return ResponseEntity.ok(resource); // 200 OK
    }
}
