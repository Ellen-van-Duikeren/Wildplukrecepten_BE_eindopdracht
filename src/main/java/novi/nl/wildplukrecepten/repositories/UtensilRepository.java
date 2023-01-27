package novi.nl.wildplukrecepten.repositories;

import novi.nl.wildplukrecepten.models.FileUpload;
import novi.nl.wildplukrecepten.models.Recipe;
import novi.nl.wildplukrecepten.models.Utensil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtensilRepository extends JpaRepository<Utensil, Long> {
//    boolean existsByUtensil(String utensil);
//
//    Long findById(Optional<Recipe> byId);
//
//    void delete(Optional<Utensil> byId);
}
