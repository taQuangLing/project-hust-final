package hust.server.domain.products.repository;

import hust.server.domain.products.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> getByIdAndActive(Long branchId, int i);
}
