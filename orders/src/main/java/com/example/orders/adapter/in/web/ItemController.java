package com.example.orders.adapter.in.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;


@Log
@RestController
@AllArgsConstructor
@Tag(name = "Items", description = "CRUD operations for items. This Endpoint could be part of a E-Commerce API that stores all the available items on the site")
public class ItemController {

    private final CreateItemUseCase createItemUseCase;

    private final GetItemsUseCase getItemsUseCase;

    private final DeleteItemUseCase deleteItemUseCase;

    private final GetItemByIdUseCase getItemByIdUseCase;

    private final UpdateItemUseCase updateItemUseCase;

    private static final ItemModelMapper mapper = new ItemModelMapper();

    @Operation(
        summary = "Create a new item",
        description = "Creates a new item using the provided name."
    )
    @ApiResponse(
        responseCode = "201",
        description = "Item successfully created",
        content = @Content(schema = @Schema(implementation = ItemModel.class))
    )
    @PostMapping("items")
    public ResponseEntity<EntityModel<ItemModel>> createItem(@RequestBody CreateItemDTO entity) {

        log.info("Endpoint for creation of new Item has been called");
        
        CreateItemCommand command = new CreateItemCommand(entity.getName());
        Item item = createItemUseCase.createItem(command);

        ItemModel model = mapper.toItemModel(item);

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

    @Operation(
        summary = "Delete a new item",
        description = "Deletes the item with the provided id."
    )
    @ApiResponse(
        responseCode = "204",
        description = "Item deleted successfully"
    )
    @DeleteMapping("items/{id}")
    public ResponseEntity<EntityModel<Void>> deleteItem(@PathVariable Long id) {

        this.deleteItemUseCase.deleteItem(new DeleteItemCommand(id));

        log.info("Item has been deleted");

        return ResponseEntity.status(204).build();
    }

    @Operation(
        summary = "Get a item",
        description = "Returns the item with the provided id."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Item successfully returned",
        content = @Content(schema = @Schema(implementation = ItemModel.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Item not found"
    )
    @GetMapping("items/{id}")
    public ResponseEntity<EntityModel<ItemModel>> getItemById(@PathVariable Long id) {

        Item item = this.getItemByIdUseCase.getItemById(new GetItemByIdCommand(id));

        ItemModel model = mapper.toItemModel(item);

        EntityModel<ItemModel> resource = EntityModel.of(model,
                linkTo(methodOn(ItemController.class).getItemById(id)).withSelfRel(),
                linkTo(methodOn(ItemController.class).getAllItems()).withRel("items")
        );

        return ResponseEntity.ok(resource); // 200 OK
    }

    @Operation(
        summary = "Get all items",
        description = "Returns all items"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Item successfully created",
        content = @Content(
            array = @ArraySchema(schema = @Schema(implementation = ItemModel.class))
        )
    )
    @GetMapping("items")
    public CollectionModel<EntityModel<ItemModel>> getAllItems() {

        List<EntityModel<ItemModel>> itemModels = getItemsUseCase.getAllItems()
            .stream()
            .map(item -> {
                ItemModel model = mapper.toItemModel(item);

                return EntityModel.of(model,
                    linkTo(methodOn(ItemController.class).getItemById(item.getId())).withSelfRel()
                );
            })
            .collect(Collectors.toList());

        return CollectionModel.of(itemModels,
            linkTo(methodOn(ItemController.class).getAllItems()).withSelfRel()
        );

    }

    @Operation(
        summary = "Update a item",
        description = "Updates a item. The id in the URL must match the id in the body"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Item successfully updated",
        content = @Content(schema = @Schema(implementation = ItemModel.class))
    )
    @ApiResponse(
        responseCode = "404",
        description = "Item not found"
    )
    @PutMapping("items/{id}")
    public ResponseEntity<EntityModel<ItemModel>> putMethodName(@PathVariable Long id, @RequestBody UpdateItem entity) throws ItemUpdateValidationException {
        
        Item updated = new Item(entity.getId(), entity.getName());

        Item result = this.updateItemUseCase.updateItem(new UpdateItemCommand(id, updated));

        ItemModel model = mapper.toItemModel(result);

        EntityModel<ItemModel> resource = EntityModel.of(model,
                            linkTo(methodOn(ItemController.class).getItemById(id)).withSelfRel(),
                            linkTo(methodOn(ItemController.class).getAllItems()).withRel("items")
                    );

        return ResponseEntity.ok(resource); 
    }
}
