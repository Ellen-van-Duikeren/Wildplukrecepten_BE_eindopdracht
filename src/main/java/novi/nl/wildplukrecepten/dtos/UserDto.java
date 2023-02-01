package novi.nl.wildplukrecepten.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import novi.nl.wildplukrecepten.models.Authority;

import javax.validation.constraints.NotBlank;
import java.util.Objects;
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

    //equals & hashcode......................................................................................................
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(username, userDto.username) && Objects.equals(password, userDto.password) && Objects.equals(enabled, userDto.enabled) && Objects.equals(apikey, userDto.apikey) && Objects.equals(firstname, userDto.firstname) && Objects.equals(lastname, userDto.lastname) && Objects.equals(emailadress, userDto.emailadress) && Objects.equals(authorities, userDto.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, enabled, apikey, firstname, lastname, emailadress, authorities);
    }
}