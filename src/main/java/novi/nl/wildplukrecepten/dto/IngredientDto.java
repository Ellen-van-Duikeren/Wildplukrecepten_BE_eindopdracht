package novi.nl.wildplukrecepten.dto.Dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Getter;
import lombok.Setter;
import novi.nl.wildplukrecepten.models.Recipe;

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

}
