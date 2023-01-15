package novi.nl.wildplukrecepten.dto.outputDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import novi.nl.wildplukrecepten.models.*;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter
public class RecipeOutputDto {
    private Long id;
    @NotBlank
    private String title;
    private String sub_title;
    private Integer persons;
    private String source;
    private String story;

    private String prep_time;
    private String cook_time;

    List<Recipe.Months> months;
    List<Recipe.Tags> tags;

    //relations............................................
    @JsonIncludeProperties({"id", "amount", "unit", "ingredient_name"})
    private List<Ingredient> ingredients;
    @JsonIncludeProperties({"id", "instruction"})
    private List<Instruction> instructions;
    @JsonIncludeProperties({"id", "utensil"})
    private List<Utensil> utensils;
    @JsonIncludeProperties({"fileName", "url"})
    private FileUpload file;

}
