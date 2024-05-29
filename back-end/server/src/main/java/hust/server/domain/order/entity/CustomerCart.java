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
@Table(name = "customer_cart")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerCart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "user_id")
    Long userId;

    @OneToMany
    @JoinColumn(name = "customer_cart_id")
    List<CustomerCartItem> cartItemList;
}
