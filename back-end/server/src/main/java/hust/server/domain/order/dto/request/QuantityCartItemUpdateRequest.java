package hust.server.domain.order.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuantityCartItemUpdateRequest {
    private Long cartItemId;
    private Integer quantity;
}
