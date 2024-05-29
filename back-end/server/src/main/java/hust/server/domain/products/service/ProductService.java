package hust.server.domain.products.service;

import hust.server.app.exception.ApiException;
import hust.server.domain.products.dto.response.ProductDetailsGuestResponse;
import hust.server.domain.products.entity.Product;
import hust.server.domain.products.repository.ProductRepository;
import hust.server.infrastructure.enums.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductDetailsGuestResponse getProductDetails(Long id) {
        Product product = productRepository.getProductById(id).orElse(null);
        if (product == null)throw new ApiException(MessageCode.ID_NOT_FOUND);
        return product.toProductDetailsGuestResponse();
    }
}
