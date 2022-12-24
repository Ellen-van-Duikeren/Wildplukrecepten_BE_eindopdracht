package novi.nl.wildplukrecepten.dto.outputDto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Getter;
import lombok.Setter;
import novi.nl.wildplukrecepten.models.Recipe;


@Getter
@Setter

public class UtensilOutputDto {
    private Long id;
    private String utensil;

    //relations............................................
    @JsonIncludeProperties({"id", "title"})
    private Recipe recipe;
}
