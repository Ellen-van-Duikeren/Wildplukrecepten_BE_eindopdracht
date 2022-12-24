package novi.nl.wildplukrecepten.dto.inputDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter

public class MessageInputDto {
    private Long id;

    private String message;
    private String fromEmail;
    private String toEmail;
}
