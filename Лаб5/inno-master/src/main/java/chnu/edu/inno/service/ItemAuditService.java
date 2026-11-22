
package chnu.edu.inno.service;

import chnu.edu.inno.model.Item;
import lombok.AllArgsConstructor;
import org.javers.core.Javers;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class ItemAuditService {

    private final Javers javers;
    private final ObjectMapper objectMapper;

    public List<JsonNode> getItemHistory(String id) {
        var snapshots = javers.findSnapshots(
                QueryBuilder.byInstanceId(id, Item.class).build()
        );

        String json = javers.getJsonConverter().toJson(snapshots);

        JsonNode root = objectMapper.readTree(json);

        return StreamSupport.stream(root.spliterator(), false)
                .toList();
    }
}
