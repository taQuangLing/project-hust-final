package hust.server.domain.order.repository;

import hust.server.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getByUserId(String userId);

    Optional<Order> getById(Long id);

    List<Order> getByBranchId(Long id);
}
