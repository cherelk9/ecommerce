package cm.backend.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cm.backend.ecommerce.models.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

    boolean existsByEmail(String email);

    Optional<Users> findByUsername(String username);

    boolean existsByUsername(String username);

}
