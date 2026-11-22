package chnu.edu.inno.service;


/*
  @author   george
  @project   inno
  @class  ItemService
  @version  1.0.0 
  @since 27/09/2026 - 19.05
*/

import chnu.edu.inno.dto.ItemRequestDto;
import chnu.edu.inno.dto.ItemResponseDto;
import chnu.edu.inno.dto.response.ApiResponse;
import chnu.edu.inno.dto.response.BaseMetaData;
import chnu.edu.inno.dto.response.PaginationMetaData;
import chnu.edu.inno.exception.ItemNotFoundException;
import chnu.edu.inno.model.Item;
import chnu.edu.inno.repository.ItemRepository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public ApiResponse<BaseMetaData, ItemResponseDto> getItemById(String id) {

        Item item = findItemOrThrow(id);

        return new ApiResponse<>(
                successMeta(200),
                List.of(toDto(item))
        );
    }

    public ApiResponse<PaginationMetaData, ItemResponseDto> getPage(
            int page, int size) {

        if (page < 0 || size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Page must be >= 0 and size must be between 1 and 100"
            );
        }

        Pageable pageable = PageRequest.of(
                page, size, Sort.by("id").ascending()
        );

        Page<Item> items = itemRepository.findAll(pageable);

        PaginationMetaData meta = PaginationMetaData.builder()
                .code(200)
                .success(true)
                .number(items.getNumber())
                .size(items.getSize())
                .totalElements(items.getTotalElements())
                .totalPages(items.getTotalPages())
                .first(items.isFirst())
                .last(items.isLast())
                .build();

        List<ItemResponseDto> data = items.getContent()
                .stream()
                .map(this::toDto)
                .toList();

        return new ApiResponse<>(meta, data);
    }

    public ApiResponse<BaseMetaData, ItemResponseDto> createItem(
            ItemRequestDto request) {

        Item item = new Item(
                request.getName(),
                request.getCode(),
                request.getQuantity()
        );

        Item saved = itemRepository.save(item);

        return new ApiResponse<>(
                successMeta(201),
                List.of(toDto(saved))
        );
    }

    public ApiResponse<BaseMetaData, ItemResponseDto> updateItem(
            String id, ItemRequestDto request) {

        Item item = findItemOrThrow(id);

        item.setName(request.getName());
        item.setCode(request.getCode());
        item.setQuantity(request.getQuantity());

        Item updated = itemRepository.save(item);

        return new ApiResponse<>(
                successMeta(200),
                List.of(toDto(updated))
        );
    }

    public void deleteItemById(String id) {

        Item item = findItemOrThrow(id);
        itemRepository.delete(item);
    }

    private Item findItemOrThrow(String id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }

    private ItemResponseDto toDto(Item item) {
        return new ItemResponseDto(
                item.getId(),
                item.getName(),
                item.getCode(),
                item.getQuantity()
        );
    }

    private BaseMetaData successMeta(int code) {
        return BaseMetaData.builder()
                .code(code)
                .success(true)
                .build();
    }
}