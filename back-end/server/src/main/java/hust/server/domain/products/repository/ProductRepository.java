package hust.server.domain.products.repository;

import hust.server.domain.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select p.* from products p, " +
            "(select menu_item.id as id from menu, menu_item " +
            "where " +
            "menu.branch_id = :branchId and " +
            "menu_item.menu_id = menu.id and " +
            "menu.active = 1 and " +
            "menu_item.active = 1 " +
            ") menu_p " +
            "where  " +
            "p.id = menu_p.id", nativeQuery = true)
    List<Product> getProductMenu(Long branchId);

    Optional<Product> getById(Long id);

    List<Product> getByCreatedByOrderByActive(String userId);

    Optional<Product> getByIdAndCreatedBy(Long id, String createdBy);
}
