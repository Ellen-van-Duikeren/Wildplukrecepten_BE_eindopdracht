package novi.nl.wildplukrecepten.repositories;

import novi.nl.wildplukrecepten.models.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {


}
