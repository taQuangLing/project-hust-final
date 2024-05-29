package hust.server.domain.order.entity;

import hust.server.domain.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "table_id")
    Integer tableId;

    @Column(name = "token")
    String token;

    @Column(name = "status")
    Integer status;

    @Column(name = "branch_id")
    Long branchId;

    @Column(name = "total")
    Long total;

    @Column(name = "user_id")
    String userId;

    @Column(name = "grand_total")
    Long grandTotal;

    @Column(name = "discount")
    Long discount;

    @Column(name = "promotion_id")
    Integer promotionId;

    @Column(name = "note")
    String note;

    @OneToMany
    @JoinColumn(name = "order_id")
    List<OrderItem> orderItemList;
}
