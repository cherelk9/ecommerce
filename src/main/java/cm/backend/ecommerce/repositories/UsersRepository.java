package cm.backend.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cm.backend.ecommerce.models.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

    boolean existsByEmail(String email);

}
