package novi.nl.wildplukrecepten.dto.Dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Getter;
import lombok.Setter;
import novi.nl.wildplukrecepten.models.Recipe;

@Getter
@Setter
public class InstructionDto {
    private Long id;
    private String instruction;

//relations............................................
@JsonIncludeProperties({"id", "title"})
    private Recipe recipe;
}
