package novi.nl.wildplukrecepten.repositories;

import novi.nl.wildplukrecepten.models.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructionRepository extends JpaRepository<Instruction, Long> {

}
