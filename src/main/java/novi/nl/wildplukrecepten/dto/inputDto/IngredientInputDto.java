package novi.nl.wildplukrecepten.dto.inputDto;

import lombok.Getter;
import lombok.Setter;
import novi.nl.wildplukrecepten.models.Recipe;

@Getter
@Setter
public class IngredientInputDto {
    private Long id;
    private Double amount;
    private String unit;
    private String ingredient_name;
    //relations............................................
    private Recipe recipe;
}
