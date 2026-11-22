package chnu.edu.inno.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<M extends BaseMetaData, D> {
    private M meta;
    private List<D> data;
}