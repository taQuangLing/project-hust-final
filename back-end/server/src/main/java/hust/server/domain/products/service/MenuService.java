package hust.server.domain.products.service;

import hust.server.app.exception.ApiException;
import hust.server.domain.products.dto.response.CategoryGuestResponse;
import hust.server.domain.products.dto.response.MenuGuestResponse;
import hust.server.domain.products.dto.response.ProductGuestResponse;
import hust.server.domain.products.entity.Branch;
import hust.server.domain.products.entity.Category;
import hust.server.domain.products.entity.Menu;
import hust.server.domain.products.entity.Product;
import hust.server.domain.products.repository.BranchRepository;
import hust.server.domain.products.repository.CategoryRepository;
import hust.server.domain.products.repository.MenuRepository;
import hust.server.domain.products.repository.ProductRepository;
import hust.server.infrastructure.enums.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private BranchRepository branchRepository;
    public MenuGuestResponse getMenuForGuest(Long branchId) {
        // Check branch id
        Branch branch = branchRepository.getById(branchId).orElse(null);
        if (branch == null)throw new ApiException(MessageCode.BRANCH_NOT_EXIST);

        if (branch.getActive() == 0)throw new ApiException(MessageCode.BRANCH_INACTIVE);

        Menu menu = menuRepository.getByBranchIdAndActive(branchId, 1).orElse(null);
        if (menu == null)throw new ApiException(MessageCode.MENU_NOT_EXIST);

        List<Product> products = productRepository.getProductMenu(branchId);

        List<Category> categories = categoryRepository.getByActive(1);

        MenuGuestResponse response = new MenuGuestResponse();

        for(Category category : categories){
            if (category.getActive() == 0)continue;
            CategoryGuestResponse categoryGuestResponse = category.toCategoryGuestResponse();
            int count = 0;
            for (Product product : products){
                if (product.getCategoryId() == category.getId()){
                    count++;
                    ProductGuestResponse productGuestResponse = product.toProductGuestResponse();
                    categoryGuestResponse.getProducts().add(productGuestResponse);
                }
            }
            if (count > 0){
                categoryGuestResponse.setCount(count);
                response.getCategories().add(categoryGuestResponse);
            }

        }

        return response;
    }
}
