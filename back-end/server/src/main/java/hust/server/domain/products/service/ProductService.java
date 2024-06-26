package hust.server.domain.products.service;

import hust.server.app.exception.ApiException;
import hust.server.domain.order.entity.Order;
import hust.server.domain.products.dto.request.AdminCategoryRequest;
import hust.server.domain.products.dto.request.AdminProductRequest;
import hust.server.domain.products.dto.response.AdminBranchResponse;
import hust.server.domain.products.dto.response.AdminCategoryResponse;
import hust.server.domain.products.dto.response.AdminProductResponse;
import hust.server.domain.products.dto.response.GuestProductDetailsResponse;
import hust.server.domain.products.entity.Category;
import hust.server.domain.products.entity.Menu;
import hust.server.domain.products.entity.MenuItem;
import hust.server.domain.products.entity.Product;
import hust.server.domain.products.repository.CategoryRepository;
import hust.server.domain.products.repository.MenuItemRepository;
import hust.server.domain.products.repository.MenuRepository;
import hust.server.domain.products.repository.ProductRepository;
import hust.server.infrastructure.enums.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    public GuestProductDetailsResponse getProductDetails(Long id) {
        Product product = productRepository.getById(id).orElse(null);
        if (product == null)throw new ApiException(MessageCode.ID_NOT_FOUND);
        return product.toProductDetailsGuestResponse();
    }

    public List<AdminProductResponse> getProductsByAdmin(String userId) {
        List<Product> productList = productRepository.getByCreatedByOrderByActive(userId);
        Collections.sort(productList, Comparator.comparing(Product::getCreatedAt).reversed());

        return productList.stream().map(item -> {
            Category category = categoryRepository.getById(item.getCategoryId()).orElse(null);
            if (category == null)throw new ApiException(MessageCode.ID_NOT_FOUND, "categoryId = "+ item.getCategoryId());
            AdminProductResponse rspItem = item.toAdminProductResponse();
            rspItem.setCategoryName(category.getName());
            return rspItem;
        }).collect(Collectors.toList());
    }

    public List<AdminCategoryResponse> getCategories(String userId) {
        List<Category> categories = categoryRepository.getByCreatedBy(userId);
        return categories.stream().map(Category::toAdminCategoryResponse).collect(Collectors.toList());
    }

    @Transactional
    public MessageCode addProduct(AdminProductRequest request) {
        Product product = request.toProductEntity();
        try{
            productRepository.save(product);
            if (request.getIsAllForMenu()){
                List<Menu> menuList = menuRepository.getByCreatedBy(request.getUserId());
                for (Menu item : menuList){
                    MenuItem menuItem = new MenuItem();
                    menuItem.setProductId(product.getId());
                    menuItem.setActive(1);
                    item.getMenuItemList().add(menuItem);
                    try {
                        menuItemRepository.save(menuItem);
                        menuRepository.save(item);
                    }catch (Exception e){
                        throw new ApiException(e, MessageCode.FAIL);
                    }
                }
            }

            return MessageCode.SUCCESS;
        }catch (Exception e) {
            throw new ApiException(e, MessageCode.FAIL);
        }
    }

    public MessageCode createCategory(AdminCategoryRequest request) {
        Category category = request.toCategoryEntity();
        try {
            categoryRepository.save(category);
            return MessageCode.SUCCESS;
        }catch (Exception e){
            throw new ApiException(e, MessageCode.FAIL);
        }
    }

    public MessageCode deleteProduct(Long id, String userId) {
        Product product = productRepository.getById(id).orElse(null);
        if (product == null)throw new ApiException(MessageCode.ID_NOT_FOUND, "productId = " + id);

        if (product.getCreatedBy().equals(userId))throw new ApiException(MessageCode.RESOURCES_AUTHORIZATION);
        try {
            productRepository.delete(product);
            return MessageCode.SUCCESS;
        }catch (Exception e){
            throw new ApiException(e, MessageCode.FAIL);
        }
    }
}
