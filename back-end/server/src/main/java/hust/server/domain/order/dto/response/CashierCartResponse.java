package hust.server.domain.order.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CashierCartResponse {
    private Long cartId;
    private List<CashierCartItemResponse> cartItemList;
    private Long orderId;
}
