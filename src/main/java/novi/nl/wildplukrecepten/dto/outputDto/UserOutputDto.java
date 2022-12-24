package novi.nl.wildplukrecepten.dto.outputDto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import novi.nl.wildplukrecepten.models.Authority;
import novi.nl.wildplukrecepten.models.UserAccount;

import java.util.Set;

@Getter
@Setter

public class UserOutputDto {

    public String username;
    public String password;
    public Boolean enabled;
    public String apikey;

    @JsonSerialize
    public Set<Authority> authorities;

    private UserAccount userAccount;

}