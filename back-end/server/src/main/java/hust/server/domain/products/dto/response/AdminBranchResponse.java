package hust.server.domain.products.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminBranchResponse {
    private Long id;
    private String address;
}