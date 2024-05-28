package hust.server.domain.products.repository;

import hust.server.domain.products.dto.response.MenuGuestResponse;
import hust.server.domain.products.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>, CustomMenuRepository {

    Optional<Menu> getByBranchIdAndActive(Long branchId, int i);
}