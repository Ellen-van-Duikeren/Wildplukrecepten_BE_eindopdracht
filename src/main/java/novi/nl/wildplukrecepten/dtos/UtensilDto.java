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

public class UtensilDto {
    private Long id;
    private String utensil;

    //relations............................................
    @JsonIncludeProperties({"id", "title"})
    private Recipe recipe;


    //equals & hashcode......................................................................................................
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtensilDto that = (UtensilDto) o;
        return Objects.equals(id, that.id) && Objects.equals(utensil, that.utensil) && Objects.equals(recipe, that.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, utensil, recipe);
    }

}
