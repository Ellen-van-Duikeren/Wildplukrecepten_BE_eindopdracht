package novi.nl.wildplukrecepten.dto.outputDto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import novi.nl.wildplukrecepten.models.Authority;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter

public class UserOutputDto {
    @NotBlank
    public String username;
    @NotBlank
    public String password;
    public Boolean enabled;
    public String apikey;
    public String firstname;
    public String lastname;
    public String emailadress;

    @JsonSerialize
    public Set<Authority> authorities;
}