package novi.nl.wildplukrecepten.dto.inputDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter

public class MessageInputDto {
    private Long id;

    private String message;
    private String emailfrom;
    private String emailto;
    private Date currentdatetime;
}
