package hust.server.domain.order.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemRequest {
    private Long productId;
    private Integer quantity;
    private Long sizeSelectedId;
}
