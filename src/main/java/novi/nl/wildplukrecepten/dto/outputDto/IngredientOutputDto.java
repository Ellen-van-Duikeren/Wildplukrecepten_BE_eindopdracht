package novi.nl.wildplukrecepten.dto.outputDto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Getter;
import lombok.Setter;
import novi.nl.wildplukrecepten.models.Recipe;

@Getter
@Setter
public class IngredientOutputDto {
    private Long id;
    private Double amount;
    private String unit;
    private String ingredient_name;


    // relations............................................
    @JsonIncludeProperties({"id", "title"})
    private Recipe recipe;

}
