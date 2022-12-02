package com.housebooking.app.service;

import com.housebooking.app.model.EmailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

	@Autowired 
	private JavaMailSender javaMailSender;
	
    @Value("${spring.mail.username}") private String sender;
	
	public int sendSimpleMail(EmailModel details)
    {
 
        // Try block to check for exceptions
        try {
 
            // Creating a simple mail message
        	SimpleMailMessage mailMessage
            = new SimpleMailMessage();
            
            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(new String[]{details.getRecipient()});
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            System.out.println("------------------body"+ mailMessage.getFrom()+"recep============"+mailMessage.getTo()+"recc--++"+details.getRecipient());
            // Sending the mail
            javaMailSender.send(mailMessage);
            return 1;
        }
 
        // Catch block to handle the exceptions
        catch (Exception e) {
            return 0;
        }
    }
}
