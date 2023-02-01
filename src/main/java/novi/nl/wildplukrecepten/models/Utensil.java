package novi.nl.wildplukrecepten.models;

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
@Table(name = "utensils")
public class Utensil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String utensil;


    //relations............................................
    @ManyToOne
    private Recipe recipe;


    //equals & hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utensil utensil1 = (Utensil) o;
        return Objects.equals(id, utensil1.id) && Objects.equals(utensil, utensil1.utensil) && Objects.equals(recipe, utensil1.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, utensil, recipe);
    }
}