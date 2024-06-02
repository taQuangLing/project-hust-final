package hust.server.domain.order.dto.request;

import hust.server.domain.order.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class CartItemCreationRequest {
    private String userId;
    private Long productId;
    private Long sizeSelectedId;
    private Integer quantity;
    private String note;

    public CartItem toCustomerCartItemEntity() {
        return CartItem.builder()
                .sizeSelectedId(sizeSelectedId)
                .quantity(quantity)
                .note(note)
                .build();
    }
}
