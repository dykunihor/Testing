package chnu.edu.inno.controller;


/*
  @author   george
  @project   inno
  @class  ItemController
  @version  1.0.0 
  @since 27/09/2026 - 19.09
*/

import chnu.edu.inno.dto.ItemRequestDto;
import chnu.edu.inno.dto.ItemResponseDto;
import chnu.edu.inno.dto.response.ApiResponse;
import chnu.edu.inno.dto.response.BaseMetaData;
import chnu.edu.inno.dto.response.PaginationMetaData;
import chnu.edu.inno.service.ItemService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BaseMetaData, ItemResponseDto>>
    getItemById(@PathVariable String id) {

        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PaginationMetaData, ItemResponseDto>>
    getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return ResponseEntity.ok(itemService.getPage(page, size));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BaseMetaData, ItemResponseDto>>
    createItem(@Valid @RequestBody ItemRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(itemService.createItem(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BaseMetaData, ItemResponseDto>>
    updateItem(
        @PathVariable String id,
        @Valid @RequestBody ItemRequestDto request) {

        return ResponseEntity.ok(
                itemService.updateItem(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable String id) {

        itemService.deleteItemById(id);

        return ResponseEntity.noContent().build();
    }
}