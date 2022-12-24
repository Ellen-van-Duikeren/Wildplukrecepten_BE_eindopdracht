package novi.nl.wildplukrecepten.dto.inputDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter

public class UserAccountInputDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String emailadress;
}
