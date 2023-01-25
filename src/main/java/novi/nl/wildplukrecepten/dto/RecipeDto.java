package novi.nl.wildplukrecepten.dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import novi.nl.wildplukrecepten.models.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter
public class RecipeDto {
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


    //equals & hashcode......................................................................................................
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeDto recipeDto = (RecipeDto) o;
        return Objects.equals(id, recipeDto.id) && Objects.equals(title, recipeDto.title) && Objects.equals(sub_title, recipeDto.sub_title) && Objects.equals(persons, recipeDto.persons) && Objects.equals(source, recipeDto.source) && Objects.equals(story, recipeDto.story) && Objects.equals(prep_time, recipeDto.prep_time) && Objects.equals(cook_time, recipeDto.cook_time) && Objects.equals(months, recipeDto.months) && Objects.equals(tags, recipeDto.tags) && Objects.equals(ingredients, recipeDto.ingredients) && Objects.equals(instructions, recipeDto.instructions) && Objects.equals(utensils, recipeDto.utensils) && Objects.equals(file, recipeDto.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, sub_title, persons, source, story, prep_time, cook_time, months, tags, ingredients, instructions, utensils, file);
    }
}
