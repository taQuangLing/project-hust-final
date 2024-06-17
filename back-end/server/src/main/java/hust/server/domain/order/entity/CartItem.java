package hust.server.domain.order.entity;

import hust.server.app.exception.ApiException;
import hust.server.domain.BaseEntity;
import hust.server.domain.order.dto.response.CashierCartItemResponse;
import hust.server.domain.order.dto.response.CustomerCartResponse;
import hust.server.domain.products.entity.Product;
import hust.server.domain.products.entity.ProductSize;
import hust.server.infrastructure.enums.MessageCode;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static hust.server.infrastructure.utilies.Utility.formatCurrency;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@Table(name = "cart_item")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "is_check")
    Integer isCheck;

    @OneToOne
    @JoinColumn(name = "product_id")
    Product product;

    @Column(name = "size_selected_id")
    Long sizeSelectedId;

    @Column(name = "quantity")
    Integer quantity;

    @Column
    String note;

    public CustomerCartResponse toCustomerCartItemResponse(){
        String sizeSelect = null;
        if (product.getHasSize() == 1){
            ProductSize productSize = product.getSizeList().stream().filter(item -> item.getId().equals(sizeSelectedId)).findAny().orElse(null);
            if (productSize == null)throw new ApiException(MessageCode.ERROR);
            sizeSelect = productSize.getSize();
        }
        return CustomerCartResponse.builder()
                .id(id)
                .name(product.getName())
                .isCheck(isCheck)
                .img(product.getImg())
                .priceDisplay(formatCurrency(product.getPrice()))
                .price(product.getPrice())
                .hasSize(product.getHasSize())
                .quantity(quantity)
                .sizeSelected(sizeSelect)
                .note(note)
                .build();
    }
    public CashierCartItemResponse toCashierCartItemResponse(){
        String sizeSelect = null;
        if (product.getHasSize() == 1){
            ProductSize productSize = product.getSizeList().stream().filter(item -> item.getId().equals(sizeSelectedId)).findAny().orElse(null);
            if (productSize == null)throw new ApiException(MessageCode.ERROR);
            sizeSelect = productSize.getSize();
        }
        return CashierCartItemResponse.builder()
                .id(id)
                .name(product.getName())
                .priceDisplay(formatCurrency(product.getPrice()))
                .price(product.getPrice())
                .hasSize(product.getHasSize())
                .quantity(quantity)
                .sizeSelected(sizeSelect)
                .build();
    }
}
