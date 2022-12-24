package novi.nl.wildplukrecepten.dto.inputDto;

import lombok.Getter;
import lombok.Setter;
import novi.nl.wildplukrecepten.models.Recipe;

@Getter
@Setter

public class UtensilInputDto {
    private Long id;
    private String utensil;

    //relations............................................
    private Recipe recipe;


}
