package novi.nl.wildplukrecepten.dto.inputDto;

import lombok.Getter;
import lombok.Setter;
import novi.nl.wildplukrecepten.models.Recipe;

@Getter
@Setter

public class InstructionInputDto {
    private Long id;
    private String instruction;

//relations............................................
    private Recipe recipe;

}
