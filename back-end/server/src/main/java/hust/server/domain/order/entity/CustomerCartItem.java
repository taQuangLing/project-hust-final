package hust.server.domain.order.entity;

import hust.server.domain.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@Table(name = "customer_cart_item")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerCartItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "is_check")
    Integer isCheck;

    @Column(name = "product_id")
    Long productId;

    @Column(name = "product_size")
    Long productSize;

    @Column(name = "quantity")
    Integer quantity;
}
