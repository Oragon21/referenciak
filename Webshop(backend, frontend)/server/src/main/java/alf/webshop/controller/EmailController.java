package alf.webshop.controller;

import alf.webshop.domain.Email;
import alf.webshop.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contact")
public class EmailController {
    Logger logger = LoggerFactory.getLogger(EmailController.class);

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }
    

    @PostMapping()
    public void sendMail(@RequestBody Email email){
        logger.info("Email kuldes");
        emailService.SendSimpleEmail(email.getTarget(), email.getSubject(), email.getBody());
    }
}
