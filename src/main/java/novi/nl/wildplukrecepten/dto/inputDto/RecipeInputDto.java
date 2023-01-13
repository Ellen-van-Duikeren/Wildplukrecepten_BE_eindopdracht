package novi.nl.wildplukrecepten.dto.inputDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import novi.nl.wildplukrecepten.dto.outputDto.IngredientOutputDto;
import novi.nl.wildplukrecepten.dto.outputDto.InstructionOutputDto;
import novi.nl.wildplukrecepten.dto.outputDto.UtensilOutputDto;
import novi.nl.wildplukrecepten.models.*;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter

public class RecipeInputDto {
    private Long id;

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
    private List<Ingredient> ingredients;
    private List<Instruction> instructions;
    private List<Utensil> utensils;
    private FileUpload file;


}
