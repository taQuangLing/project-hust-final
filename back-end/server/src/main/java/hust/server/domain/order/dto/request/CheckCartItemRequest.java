package hust.server.domain.order.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckCartItemRequest {
    private Long cartItemId;
    private Integer isCheck;
}
