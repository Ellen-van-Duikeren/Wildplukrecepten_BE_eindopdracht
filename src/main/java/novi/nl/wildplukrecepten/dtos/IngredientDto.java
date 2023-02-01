package novi.nl.wildplukrecepten.dtos;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import novi.nl.wildplukrecepten.models.Recipe;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter
public class IngredientDto {
    private Long id;
    private Double amount;
    private String unit;
    private String ingredient_name;


    // relations............................................
    @JsonIncludeProperties({"id", "title"})
    private Recipe recipe;


    //equals & hashcode......................................................................................................
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientDto that = (IngredientDto) o;
        return Objects.equals(id, that.id) && Objects.equals(amount, that.amount) && Objects.equals(unit, that.unit) && Objects.equals(ingredient_name, that.ingredient_name) && Objects.equals(recipe, that.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, unit, ingredient_name, recipe);
    }
}
