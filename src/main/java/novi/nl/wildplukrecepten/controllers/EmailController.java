package novi.nl.wildplukrecepten.controllers;

import novi.nl.wildplukrecepten.models.EmailDetails;
import novi.nl.wildplukrecepten.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    private EmailRepository emailRepository;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public String sendMail(@RequestBody EmailDetails emailDetails)
    {
        String status = emailRepository.sendSimpleMail(emailDetails);
        return status;
    }

    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails emailDetails)
    {
        String status = emailRepository.sendMailWithAttachment(emailDetails);
        return status;
    }
}
