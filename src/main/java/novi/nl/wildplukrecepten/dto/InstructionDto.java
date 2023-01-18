package novi.nl.wildplukrecepten.dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import novi.nl.wildplukrecepten.models.Recipe;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter
public class InstructionDto {
    private Long id;
    private String instruction;

    //relations............................................
    @JsonIncludeProperties({"id", "title"})
    private Recipe recipe;


}