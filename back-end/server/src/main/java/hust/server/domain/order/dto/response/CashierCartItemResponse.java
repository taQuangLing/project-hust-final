package hust.server.domain.order.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CashierCartItemResponse {
    private String name;
    private String price;
    private Long id;
    private Integer quantity;
    private Integer hasSize;
    private String sizeSelected;
//    private String note;
}
