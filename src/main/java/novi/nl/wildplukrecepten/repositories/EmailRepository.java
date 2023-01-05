package novi.nl.wildplukrecepten.repositories;


import novi.nl.wildplukrecepten.models.EmailDetails;

public interface EmailRepository {
    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
