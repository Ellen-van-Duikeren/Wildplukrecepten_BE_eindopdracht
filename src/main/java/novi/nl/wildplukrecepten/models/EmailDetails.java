package novi.nl.wildplukrecepten.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter

public class EmailDetails {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;

    public EmailDetails(String recipient, String msgBody, String subject) {
        this.recipient = recipient;
        this.msgBody = msgBody;
        this.subject = subject;
    }
}
