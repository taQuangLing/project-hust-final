package hust.server.domain.order.entity;

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
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "product_id")
    Long productId;

    @Column(name = "discount", precision = 10, scale = 2)
    Long discount;

    @Column(name = "quantity")
    Integer quantity;
}
