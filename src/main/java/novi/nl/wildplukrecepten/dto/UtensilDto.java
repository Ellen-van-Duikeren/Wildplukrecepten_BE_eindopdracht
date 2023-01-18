package novi.nl.wildplukrecepten.dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import novi.nl.wildplukrecepten.models.Recipe;

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
}
