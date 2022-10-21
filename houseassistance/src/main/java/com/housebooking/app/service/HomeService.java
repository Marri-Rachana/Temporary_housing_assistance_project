package com.housebooking.app.service;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.housebooking.app.dao.HomeRepo;
import com.housebooking.app.model.EmailModel;
import com.housebooking.app.model.UserModel;
import org.springframework.mail.SimpleMailMessage;

@Service
public class HomeService {
	
	@Autowired
	private HomeRepo homeRepo;
	@Autowired 
	private JavaMailSender javaMailSender;
	 
    @Value("${spring.mail.username}") private String sender;
	
	public void saveUser(UserModel user) {
		System.out.println("save===user===service");
		homeRepo.save(user);
		
	}
	
	public UserModel authenticateUser(UserModel usermodel) {
		List<UserModel> user = homeRepo.findAll();
		List<UserModel> veifiedUser = user.stream().filter(n -> n.getEmail().equals(usermodel.getEmail()) && n.getPassword().equals(usermodel.getPassword())).collect(Collectors.toList());
		
		System.out.println(veifiedUser.get(0));
		
		return veifiedUser.get(0);
		
	}

	public UserModel validateUsername(UserModel user) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserModel validatePassword(UserModel usermodel) {
		List<UserModel> user = homeRepo.findAll();
		List<UserModel> veifiedUser = user.stream().filter(n -> n.getEmail().equals(usermodel.getEmail()) && n.getSecurityQuestion().equals(usermodel.getSecurityQuestion())  && n.getSecurityAnswer().equals(usermodel.getSecurityAnswer())).collect(Collectors.toList());
		return veifiedUser.get(0);
	}

	public void saveNewPassword(UserModel usermodel) {
		
		UserModel user = homeRepo.findbyEmail(usermodel.getEmail());
		System.out.println("user#########"+user.toString());
		user.setPassword(usermodel.getPassword());
		homeRepo.save(user);
	}
	
  
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

	public UserModel findUser(String email) {
		List<UserModel> user = homeRepo.findAll();
		List<UserModel> veifiedUser = user.stream().filter(n -> n.getEmail().equals(email)).collect(Collectors.toList());
		return veifiedUser.get(0);
		
	}
	 
}
