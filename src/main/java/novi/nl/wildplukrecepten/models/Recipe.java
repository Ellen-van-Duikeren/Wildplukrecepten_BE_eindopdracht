package novi.nl.wildplukrecepten.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String sub_title;
    private Integer persons;
    private String source;
    private String story;

    private String prep_time;
    private String cook_time;


    // enums............................................................................................................
    public enum Months {
        JANUARI,
        FEBRUARI,
        MAART,
        APRIL,
        MEI,
        JUNI,
        JULI,
        AUGUSTUS,
        SEPTEMBER,
        OKTOBER,
        NOVEMBER,
        DECEMBER,
        JAARROND
    }

    @ElementCollection(targetClass = Months.class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    List<Months> months;

    public enum Tags {
        VEGETARISCH,
        VEGANISTISCH,
        LACTOSEVRIJ,
        GLUTENVRIJ,
        ONTBIJT,
        LUNCH,
        DINER,
        SNACK,
        BIJGERECHT,
        VOORGERECHT,
        HOOFDGERECHT,
        DRINKEN,
        ALCOHOLISCH,
        OPENVUUR,
        DUTCHOVEN
    }

    @ElementCollection(targetClass = Tags.class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    List<Tags> tags;


    // relations......................................................................................................
    @OneToMany(mappedBy = "recipe")
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe")
    private List<Instruction> instructions;


    @OneToMany(mappedBy = "recipe")
    private List<Utensil> utensils;


    @OneToOne
    FileUpload file;


    //methods............................................
    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public void addInstruction(Instruction instruction) {

        this.instructions.add(instruction);
    }

    public void addUtensil(Utensil utensil) {
        this.utensils.add(utensil);
    }


    //equals & hashcode......................................................................................................
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(id, recipe.id) && Objects.equals(title, recipe.title) && Objects.equals(sub_title, recipe.sub_title) && Objects.equals(persons, recipe.persons) && Objects.equals(source, recipe.source) && Objects.equals(story, recipe.story) && Objects.equals(prep_time, recipe.prep_time) && Objects.equals(cook_time, recipe.cook_time) && Objects.equals(months, recipe.months) && Objects.equals(tags, recipe.tags) && Objects.equals(ingredients, recipe.ingredients) && Objects.equals(instructions, recipe.instructions) && Objects.equals(utensils, recipe.utensils) && Objects.equals(file, recipe.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, sub_title, persons, source, story, prep_time, cook_time, months, tags, ingredients, instructions, utensils, file);
    }
}
