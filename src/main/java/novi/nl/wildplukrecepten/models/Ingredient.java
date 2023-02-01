package novi.nl.wildplukrecepten.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter

@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Double amount;
    private String unit;
    private String ingredient_name;


    //relations............................................
    @ManyToOne
    private Recipe recipe;


    //equals & hashcode......................................................................................................
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(id, that.id) && Objects.equals(amount, that.amount) && Objects.equals(unit, that.unit) && Objects.equals(ingredient_name, that.ingredient_name) && Objects.equals(recipe, that.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, unit, ingredient_name, recipe);
    }
}