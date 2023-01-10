package novi.nl.wildplukrecepten.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

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
    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    @OneToMany(mappedBy = "recipe")
    private List<Instruction> instructions;
    public void addInstruction(Instruction instruction) {
        this.instructions.add(instruction);
    }

    @OneToMany(mappedBy = "recipe")
    private List<Utensil> utensils;
    public void addUtensil(Utensil utensil) {
        this.utensils.add(utensil);
    }

    @OneToOne
    FileUpload file;
}
