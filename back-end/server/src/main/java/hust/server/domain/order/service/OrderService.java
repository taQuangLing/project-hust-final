package hust.server.domain.order.service;

import hust.server.app.exception.ApiException;
import hust.server.domain.authen.entities.User;
import hust.server.domain.authen.repository.UserRepository;
import hust.server.domain.order.dto.request.OrderCreationRequest;
import hust.server.domain.order.dto.request.OrderUpdateRequest;
import hust.server.domain.order.dto.request.OrderUpdatedStatusRequest;
import hust.server.domain.order.dto.response.CashierOrderResponse;
import hust.server.domain.order.dto.response.GuestOrderResponse;
import hust.server.domain.order.entity.CartItem;
import hust.server.domain.order.entity.Order;
import hust.server.domain.order.entity.OrderItem;
import hust.server.domain.order.repository.CartItemRepository;
import hust.server.domain.order.repository.CartRepository;
import hust.server.domain.order.repository.OrderItemRepository;
import hust.server.domain.order.repository.OrderRepository;
import hust.server.domain.products.entity.ProductSize;
import hust.server.domain.products.repository.ProductRepository;
import hust.server.infrastructure.enums.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public MessageCode createOrder(OrderCreationRequest request) {
        List<OrderItem> orderItemList = new ArrayList<>();
        AtomicLong total = new AtomicLong();

        User user = userRepository.getById(request.getUserId()).orElse(null);
        if (user == null)throw new ApiException(MessageCode.ID_NOT_FOUND);

        request.getItemCartList().forEach(id -> {
            CartItem cartItem = cartItemRepository.getById(id).orElse(null);
            if (cartItem == null)throw new ApiException(MessageCode.ID_NOT_FOUND, "id = " + id);

            OrderItem orderItem = convertToOrderItem(cartItem);
            total.addAndGet(orderItem.getProductPrice() * orderItem.getQuantity());

            orderItemRepository.save(orderItem);
            orderItemList.add(orderItem);

            cartItemRepository.delete(cartItem);
        });

        Order order = new Order();
        order.setOrderItemList(orderItemList);
        order.setUserId(request.getUserId());
        order.setStatus(0);
        order.setIsOrderAtTable(request.getIsOrderAtTable());
        order.setPayments(request.getPayments());
        order.setTableNumber(request.getTableNumber());
        order.setBranchId(request.getBranchId());
        order.setTotal(total.getAcquire());
        order.setIsCustomerOrder(user.getIsGuest());

        try{
            orderRepository.save(order);
            return MessageCode.SUCCESS;
        }catch (Exception e){
            throw new ApiException(e, MessageCode.ERROR);
        }
    }

    private OrderItem convertToOrderItem(CartItem cartItem){
        long price;
        if (cartItem.getProduct().getHasSize() == 1){
            ProductSize sizeSelected = cartItem.getProduct().getSizeList()
                    .stream()
                    .filter(size -> size.getId() == cartItem.getSizeSelectedId()).findAny().orElse(null);
            if (sizeSelected == null)throw new ApiException(MessageCode.ERROR);
            price = sizeSelected.getPrice();
        }else{
            price = cartItem.getProduct().getPrice();
        }

        return OrderItem.builder()
                .note(cartItem.getNote())
                .product(cartItem.getProduct())
                .productPrice(price)
                .sizeSelectedId(cartItem.getSizeSelectedId())
                .quantity(cartItem.getQuantity())
                .build();
    }

    public List<GuestOrderResponse> getGuestOrder(String userId) {
        List<Order> orders = orderRepository.getByUserId(userId);

        return orders.stream().map(Order::toGuestOrderResponse).collect(Collectors.toList());
    }

    public List<CashierOrderResponse> getCashierOrder(String userId){
        List<Order> orders = orderRepository.getByUserId(userId);

        return orders.stream().map(Order::toCashierOrderResponse).collect(Collectors.toList());
    }

    public MessageCode updateOrderStatus(OrderUpdatedStatusRequest request) {
        Order order = orderRepository.getById(request.getId()).orElse(null);
        if (order == null)throw new ApiException(MessageCode.ID_NOT_FOUND, "id = "+ request.getId());
        order.setUpdatedBy(request.getUserId());
        order.setStatus(request.getStatus());

        try {
            orderRepository.save(order);
            return MessageCode.SUCCESS;
        }catch (Exception e){
            throw new ApiException(e, MessageCode.ERROR);
        }

    }

    public MessageCode updateOrder(OrderUpdateRequest request) {
        AtomicLong total = new AtomicLong();
        Order order = orderRepository.getById(request.getId()).orElse(null);

        if (order == null)throw new ApiException(MessageCode.ID_NOT_FOUND, "orderId = " + request.getId());
        order.setPayments(request.getPayments());
        order.setIsOrderAtTable(request.getIsOrderAtTable());
        order.getOrderItemList().clear();
        request.getItemCartList().forEach(item -> {
            CartItem cartItem = cartItemRepository.getById(item).orElse(null);
            if (cartItem == null)throw new ApiException(MessageCode.ID_NOT_FOUND, "cartItemId = " + item);
            OrderItem orderItem = convertToOrderItem(cartItem);
            order.getOrderItemList().add(orderItem);
            total.addAndGet(orderItem.getProductPrice() * cartItem.getQuantity());

            orderItemRepository.save(orderItem);
            order.getOrderItemList().add(orderItem);
            cartItemRepository.delete(cartItem);
        });

        order.setTotal(total.getAcquire());
        try{
            orderRepository.save(order);
            return MessageCode.SUCCESS;
        }catch (Exception e){
            throw new ApiException(e, MessageCode.ERROR);
        }
    }
}
