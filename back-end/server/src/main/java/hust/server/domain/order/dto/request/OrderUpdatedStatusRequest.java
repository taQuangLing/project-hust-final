package hust.server.domain.order.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderUpdatedStatusRequest {
    private Long userId;
    private Long id;
    private Integer status;
}
