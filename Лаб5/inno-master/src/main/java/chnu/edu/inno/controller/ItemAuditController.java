
package chnu.edu.inno.controller;

import chnu.edu.inno.dto.response.ApiResponse;
import chnu.edu.inno.dto.response.BaseMetaData;
import chnu.edu.inno.service.ItemAuditService;
import lombok.AllArgsConstructor;
import tools.jackson.databind.JsonNode;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/items")
public class ItemAuditController {

    private final ItemAuditService itemAuditService;

    @GetMapping("/{id}/history")
    public ResponseEntity<ApiResponse<BaseMetaData, JsonNode>>
    getItemHistory(@PathVariable String id) {

        List<JsonNode> history = itemAuditService.getItemHistory(id);

        BaseMetaData meta = BaseMetaData.builder()
                .code(200)
                .success(true)
                .build();

        return ResponseEntity.ok(
                new ApiResponse<>(meta, history)
        );
    }
}
