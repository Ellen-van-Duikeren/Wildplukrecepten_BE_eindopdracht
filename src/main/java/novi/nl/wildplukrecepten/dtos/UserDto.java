package novi.nl.wildplukrecepten.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import novi.nl.wildplukrecepten.models.Authority;

import javax.validation.constraints.NotBlank;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter

public class UserDto {
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