package hust.server.domain.order.service;

import hust.server.app.exception.ApiException;
import hust.server.domain.order.dto.request.CartItemCreationRequest;
import hust.server.domain.order.dto.request.CheckCartItemRequest;
import hust.server.domain.order.dto.request.QuantityCartItemUpdateRequest;
import hust.server.domain.order.dto.response.CashierCartItemResponse;
import hust.server.domain.order.dto.response.CashierCartResponse;
import hust.server.domain.order.dto.response.CustomerCartResponse;
import hust.server.domain.order.entity.Cart;
import hust.server.domain.order.entity.CartItem;
import hust.server.domain.order.entity.Order;
import hust.server.domain.order.repository.CartItemRepository;
import hust.server.domain.order.repository.CartRepository;
import hust.server.domain.order.repository.OrderRepository;
import hust.server.domain.products.entity.Product;
import hust.server.domain.products.repository.ProductRepository;
import hust.server.infrastructure.enums.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<CustomerCartResponse> guestGetCartList(String userId) {
        Cart cart = cartRepository.getByUserId(userId).orElse(null);
        if (cart == null)throw new ApiException(MessageCode.ID_NOT_FOUND, "userId = " + userId);

        List<CartItem> customerCarts = cart.getCartItemList();
        customerCarts.sort(new Comparator<CartItem>() {
            @Override
            public int compare(CartItem o1, CartItem o2) {
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
        });
        List<CustomerCartResponse> response = new ArrayList<>();
        customerCarts.forEach(item -> response.add(item.toCustomerCartItemResponse()));
        return response;
    }

    public List<CashierCartItemResponse> cashierGetCartList(String userId) {
        Cart cart = cartRepository.getByUserId(userId).orElse(null);
        if (cart == null)throw new ApiException(MessageCode.ID_NOT_FOUND, "userId = " + userId);


        List<CartItem> customerCarts = cart.getCartItemList();
        customerCarts.sort(new Comparator<CartItem>() {
            @Override
            public int compare(CartItem o1, CartItem o2) {
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
        });
        List<CashierCartItemResponse> response = new ArrayList<>();
        customerCarts.forEach(item -> response.add(item.toCashierCartItemResponse()));
        return response;
    }

    public MessageCode checkCart(CheckCartItemRequest request) {
        CartItem cartItem = cartItemRepository.getById(request.getCartItemId()).orElse(null);

        if (cartItem == null)throw new ApiException(MessageCode.ID_NOT_FOUND);
        cartItem.setIsCheck(request.getIsCheck());
        try{
            cartItemRepository.save(cartItem);
            return MessageCode.SUCCESS;
        }catch (Exception e){
            throw new ApiException(MessageCode.ERROR);
        }
    }

    public MessageCode updateQuantity(QuantityCartItemUpdateRequest request) {
        CartItem cartItem = cartItemRepository.getById(request.getCartItemId()).orElse(null);

        if (cartItem == null)throw new ApiException(MessageCode.ID_NOT_FOUND);
        cartItem.setQuantity(request.getQuantity());
        try{
            cartItemRepository.save(cartItem);
            return MessageCode.SUCCESS;
        }catch (Exception e){
            throw new ApiException(MessageCode.ERROR);
        }
    }

    public MessageCode addCartItem(CartItemCreationRequest request) {
        Cart cart = cartRepository.getByUserId(request.getUserId()).orElse(null);
        if (cart == null)throw new ApiException(MessageCode.ID_NOT_FOUND, "userId = " + request.getUserId());

        Product product = productRepository.getById(request.getProductId()).orElse(null);
        if (product == null)throw new ApiException(MessageCode.ID_NOT_FOUND);

        CartItem cartItem = request.toCustomerCartItemEntity();
        cartItem.setProduct(product);
        try {
            cartItemRepository.save(cartItem);
            cart.getCartItemList().add(cartItem);
            cartRepository.save(cart);
            return MessageCode.SUCCESS;
        }catch (Exception e){
            throw new ApiException(e, MessageCode.ERROR);
        }
    }

    public MessageCode deleteCartItem(Long id) {
        CartItem cartItem = cartItemRepository.getById(id).orElse(null);
        if (cartItem == null)throw new ApiException(MessageCode.ID_NOT_FOUND, "Id = " + id);

        try{
            cartItemRepository.delete(cartItem);
            return MessageCode.SUCCESS;
        }catch (Exception e){
            throw new ApiException(e, MessageCode.ERROR);
        }
    }

    public CashierCartResponse mapOrderToCart(Long orderId) {
        Order order = orderRepository.getById(orderId).orElse(null);
        if (order == null)throw new ApiException(MessageCode.ID_NOT_FOUND, "orderId = "+ orderId);

        Cart cart = cartRepository.getByUserId(order.getUserId()).orElse(null);
        if (cart != null)cartRepository.delete(cart);

        Cart cartNew = new Cart();
        cartNew.setUserId(order.getUserId());
        cartNew.setPayments(order.getPayments());
        cartNew.setIsOrderAtTable(order.getIsOrderAtTable());
        order.getOrderItemList().forEach(item -> {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(item.getProduct());
            cartItem.setQuantity(item.getQuantity());
            cartItem.setSizeSelectedId(item.getSizeSelectedId());
            cartNew.getCartItemList().add(cartItem);
            try {
                cartItemRepository.save(cartItem);
            }catch (Exception e){
                throw new ApiException(MessageCode.ERROR);
            }
            cartNew.getCartItemList().add(cartItem);
        });
        try {
            cartRepository.save(cartNew);
        }catch (Exception e){
            throw new ApiException(MessageCode.ERROR);
        }
        CashierCartResponse response = CashierCartResponse.builder()
                .cartId(cartNew.getId())
                .orderId(orderId)
                .cartItemList(new ArrayList<>())
                .build();
        cartNew.getCartItemList().forEach(item -> response.getCartItemList().add(item.toCashierCartItemResponse()));

        return response;
    }

    public MessageCode deleteCart(String userId) {
        Cart cart = cartRepository.getByUserId(userId).orElse(null);
        if (cart == null)throw new ApiException(MessageCode.ID_NOT_FOUND, "userId: " + userId);
        try {
            cartRepository.delete(cart);
            return MessageCode.SUCCESS;
        }catch(Exception e){
            throw new ApiException(e, MessageCode.ERROR);
        }
    }
}