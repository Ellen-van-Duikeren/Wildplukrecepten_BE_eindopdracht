package novi.nl.wildplukrecepten.services;


import novi.nl.wildplukrecepten.models.EmailDetails;

public interface EmailService {
    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
