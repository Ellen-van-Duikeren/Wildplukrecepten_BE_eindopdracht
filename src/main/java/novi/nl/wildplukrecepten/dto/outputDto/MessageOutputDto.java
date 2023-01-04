package novi.nl.wildplukrecepten.dto.outputDto;

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
public class MessageOutputDto {
    private Long id;

    private String message;
    private String emailfrom;
    private String emailto;
    private Date currentdatetime;
}
