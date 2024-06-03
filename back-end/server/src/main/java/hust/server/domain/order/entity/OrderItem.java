package hust.server.domain.order.entity;

import hust.server.app.exception.ApiException;
import hust.server.domain.BaseEntity;
import hust.server.domain.order.dto.response.CashierOrderItemResponse;
import hust.server.domain.order.dto.response.GuestOrderItemResponse;
import hust.server.domain.products.entity.Product;
import hust.server.domain.products.entity.ProductSize;
import hust.server.infrastructure.enums.MessageCode;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "order_item")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    Product product;

    @Column(name = "discount", precision = 10, scale = 2)
    Long discount;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "size_selected_id")
    Long sizeSelectedId;

    @Column(name = "product_price")
    Long productPrice;

    @Column
    String note;

    public GuestOrderItemResponse toGuestOrderItemResponse(){
        String sizeStr = null;
        if (product.getHasSize() == 1){
            ProductSize productSize = product.getSizeList().stream().filter(size -> size.getId().equals(sizeSelectedId)).findAny().orElse(null);
            if (productSize == null)throw new ApiException(MessageCode.ERROR);
            sizeStr = productSize.getSize();
        }

        return GuestOrderItemResponse.builder()
                .productName(product.getName())
                .productImg(product.getImg())
                .productPrice(productPrice + " đ")
                .quantity(quantity)
                .hasSize(product.getHasSize())
                .sizeSelected(sizeStr)
                .note(note)
                .id(id)
                .build();
    }

    public CashierOrderItemResponse toCashierOrderItemResponse(){
        String sizeStr = null;
        if (product.getHasSize() == 1){
            ProductSize productSize = product.getSizeList().stream().filter(size -> size.getId().equals(sizeSelectedId)).findAny().orElse(null);
            if (productSize == null)throw new ApiException(MessageCode.ERROR);
            sizeStr = productSize.getSize();
        }

        return CashierOrderItemResponse.builder()
                .productName(product.getName())
                .productPrice(productPrice + " đ")
                .quantity(quantity)
                .sizeSelected(sizeStr)
                .note(note)
                .id(id)
                .build();
    }
}