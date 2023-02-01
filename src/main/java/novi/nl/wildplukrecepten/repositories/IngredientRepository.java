package novi.nl.wildplukrecepten.repositories;

import novi.nl.wildplukrecepten.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
