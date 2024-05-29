package hust.server.domain.order.entity;

import hust.server.domain.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@Table(name = "cashier_cart")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CashierCart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "is_ordered_table")
    Integer isOrderedTable;

    @OneToMany
    @JoinColumn(name = "cashier_cart_id")
    List<CashierCartItem> cartItemList;
}
