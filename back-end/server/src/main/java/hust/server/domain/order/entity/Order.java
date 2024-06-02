package hust.server.domain.order.entity;

import hust.server.app.exception.ApiException;
import hust.server.domain.BaseEntity;
import hust.server.domain.order.dto.response.CashierOrderResponse;
import hust.server.domain.order.dto.response.GuestOrderResponse;
import hust.server.infrastructure.enums.MessageCode;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

import static hust.server.infrastructure.utilies.Utility.toLocalDateTime;

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

    @Column(name = "table_number")
    Integer tableNumber;

    @Column(name = "token")
    String token;

    @Column(name = "status")
    Integer status; // 0: Chờ xác nhận, 1: Đang pha chế, 2: Đã xong, 3: Đã hủy

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

    @OneToMany
    @JoinColumn(name = "order_id")
    List<OrderItem> orderItemList;

    @Column
    Integer payments;

    @Column(name = "is_order_at_table")
    Integer isOrderAtTable;

    @Column(name = "is_customer_order")
    Integer isCustomerOrder;

    @Column
    String code;

    @Column(name = "updated_by")
    Long updatedBy;

    public GuestOrderResponse toGuestOrderResponse(){
        String statusString = null;
        switch (status){
            case 0:
                statusString = "Chờ xác nhận";
                break;
            case 1:
                statusString = "Đang pha chế";
                break;
            case 2:
                statusString = "Đã xong";
                break;
            case 3:
                statusString = "Đã hủy";
                break;
            default:
                break;
        }

        return GuestOrderResponse.builder()
                .orderAt(toLocalDateTime(createdAt, null))
                .orderItemList(orderItemList.stream().map(OrderItem::toGuestOrderItemResponse).collect(Collectors.toList()))
                .status(statusString)
                .build();
    }

    public CashierOrderResponse toCashierOrderResponse(){
        String statusString = null;
        switch (status){
            case 0:
                statusString = "Chờ xác nhận";
                break;
            case 1:
                statusString = "Đang pha chế";
                break;
            case 2:
                statusString = "Đã xong";
                break;
            case 3:
                statusString = "Đã hủy";
                break;
            default:
                throw new ApiException(MessageCode.ERROR, "status = default");
        }

        String isOrderTableString = null;
        switch (isOrderAtTable){
            case 0:
                isOrderTableString = "Mang đi";
                break;
            case 1:
                isOrderTableString = "Tại bàn";
                break;
            default:
                throw new ApiException(MessageCode.ERROR, "isOrderAtTable = default");
        }

        String paymentsString = null;
        switch (payments){
            case 0:
                paymentsString = "Tiền mặt";
                break;
            case 1:
                paymentsString = "Chuyển khoản";
                break;
            case 2:
                paymentsString = "VNPay";
                break;
            default:
                throw new ApiException(MessageCode.ERROR, "payment = default");
        }

        return CashierOrderResponse.builder()
                .orderAt(toLocalDateTime(createdAt, null))
                .orderItemList(orderItemList.stream().map(OrderItem::toCashierOrderItemResponse).collect(Collectors.toList()))
                .status(statusString)
                .code(code)
                .isOrderAtTable(isOrderTableString)
                .payments(paymentsString)
                .id(id)
                .total(total + " đ")
                .build();
    }
}
