package hust.server.app.endpoint.rest;

import hust.server.app.service.ResponseFactory;
import hust.server.domain.products.service.MenuService;
import hust.server.domain.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/guest/v1/menu")
    public ResponseEntity<?> getMenuForGuest(@RequestParam Long branchId){
        return ResponseFactory.response(menuService.getMenuForGuest(branchId));
    }

    @GetMapping("/cashier/v1/menu")
    public ResponseEntity<?> cashierGetMenu(@RequestParam Long branchId){
        return ResponseFactory.response(menuService.cashierGetMenu(branchId));
    }
}
