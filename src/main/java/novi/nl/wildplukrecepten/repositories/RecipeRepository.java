package novi.nl.wildplukrecepten.repositories;

import novi.nl.wildplukrecepten.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
