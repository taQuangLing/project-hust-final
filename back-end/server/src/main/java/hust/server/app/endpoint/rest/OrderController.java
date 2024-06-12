package hust.server.app.endpoint.rest;

import hust.server.app.service.ResponseFactory;
import hust.server.domain.order.dto.request.OrderCreationRequest;
import hust.server.domain.order.dto.request.OrderUpdateRequest;
import hust.server.domain.order.dto.request.OrderUpdatedStatusRequest;
import hust.server.domain.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/guest/v1/orders")
    public ResponseEntity<?> createGuestOrder(@RequestBody OrderCreationRequest request){
        return ResponseFactory.response(orderService.createOrder(request));
    }

    @GetMapping("/guest/v1/orders/{userId}")
    public ResponseEntity<?> getGuestOrder(@PathVariable String userId){
        return ResponseFactory.response(orderService.getGuestOrder(userId));
    }

    @GetMapping("/cashier/v1/orders/{userId}")
    public ResponseEntity<?> getCashierOrder(@PathVariable String userId){
        return ResponseFactory.response(orderService.getCashierOrder(userId));
    }

    @PostMapping("/cashier/v1/orders")
    public ResponseEntity<?> createCashierOrder(@RequestBody OrderCreationRequest request){
        return ResponseFactory.response(orderService.createOrder(request));
    }

    @PutMapping("/cashier/v1/orders/{id}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody OrderUpdatedStatusRequest request)
    {
        request.setId(id);
        return ResponseFactory.response(orderService.updateOrderStatus(request));
    }

    @PutMapping("/cashier/v1/orders/{id}")
    public ResponseEntity<?> updateOrders(
            @PathVariable Long id,
            @RequestBody OrderUpdateRequest request){
        request.setId(id);
        return ResponseFactory.response(orderService.updateOrder(request));
    }
}
