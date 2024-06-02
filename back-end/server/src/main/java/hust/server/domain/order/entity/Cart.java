package hust.server.domain.order.entity;

import hust.server.domain.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "user_id")
    String userId;

//    @Column(name = "order_id")
//    Long orderId;
    @Column(name = "payments")
    Integer payments;

    @Column(name = "is_order_at_table")
    Integer isOrderAtTable;

    @OneToMany
    @JoinColumn(name = "customer_cart_id")
    List<CartItem> cartItemList = new ArrayList<>();

//    @Column(name = "order_id")
//    Long orderId;
}
