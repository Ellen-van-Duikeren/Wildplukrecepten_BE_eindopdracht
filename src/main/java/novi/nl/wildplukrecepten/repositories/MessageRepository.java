package novi.nl.wildplukrecepten.repositories;

import novi.nl.wildplukrecepten.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {


}
