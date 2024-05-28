package hust.server.app.endpoint.rest;

import hust.server.app.service.ResponseFactory;
import hust.server.domain.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/guest/v1/products/{id}")
    public ResponseEntity<?> getProductDetails(@PathVariable Long id){
        return ResponseFactory.response(productService.getProductDetails(id));
    }
}
