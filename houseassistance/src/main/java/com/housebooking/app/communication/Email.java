package com.housebooking.app.communication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Email implements Communication {

    @Autowired
    private JavaMailSender javaMailSender;


    public int sendSimpleMail(SimpleMailMessage mailMessage)
    {

        // Try block to check for exceptions
        try {

            javaMailSender.send(mailMessage);
            return 1;
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return 0;
        }
    }
}
