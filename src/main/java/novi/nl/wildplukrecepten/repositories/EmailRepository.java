package novi.nl.wildplukrecepten.repositories;

import novi.nl.wildplukrecepten.models.EmailDetails;

// Interface
public interface EmailRepository {

    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
