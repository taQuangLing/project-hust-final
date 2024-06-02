package hust.server.domain.authen.repository;

import hust.server.domain.authen.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByUsername(String username);

    public Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> getById(String userId);
}
