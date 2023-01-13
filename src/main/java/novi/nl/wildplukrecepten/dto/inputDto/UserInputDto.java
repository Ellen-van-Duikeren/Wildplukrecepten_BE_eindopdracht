package novi.nl.wildplukrecepten.dto.inputDto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import novi.nl.wildplukrecepten.models.Authority;

import java.util.Set;

@Getter
@Setter

public class UserInputDto {
    public String username;
    public String password;
    public Boolean enabled;
    public String apikey;
    public String firstname;
    public String lastname;
    public String emailadress;

    public Set<Authority> authorities;
}
