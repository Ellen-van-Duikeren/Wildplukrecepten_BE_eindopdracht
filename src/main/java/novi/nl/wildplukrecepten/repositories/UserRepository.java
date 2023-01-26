package novi.nl.wildplukrecepten.repositories;

import novi.nl.wildplukrecepten.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
