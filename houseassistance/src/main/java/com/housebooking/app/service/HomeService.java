package com.housebooking.app.service;
import java.util.List;
import java.util.stream.Collectors;

import com.housebooking.app.dao.AnnouncementRepo;
import com.housebooking.app.dao.UserProfileRepo;
import com.housebooking.app.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.housebooking.app.dao.HomeRepo;
import org.springframework.mail.SimpleMailMessage;

@Service
public class HomeService {
	
	@Autowired
	private HomeRepo homeRepo;
	@Autowired 
	private JavaMailSender javaMailSender;

	@Autowired
	private UserProfileRepo userProfileRepo;
    @Autowired
	private UserContactModel userContact;

	@Autowired
	private UserContactRepo userContactRepo;

	@Autowired
	private AnnouncementRepo announcementRepo;
	 
    @Value("${spring.mail.username}") private String sender;

	public int saveUser(UserModel user) {
		System.out.println("save===user===service");
		homeRepo.save(user);
		if(homeRepo.save(user)!=null) {
			return 1;
		}
		else {
			return 0;
		}


	}
	
	public UserModel authenticateUser(UserModel usermodel) {
		List<UserModel> user = homeRepo.findAll();
		List<UserModel> verifiedUser = user.stream().filter(n -> (n.getEmail().equals(usermodel.getEmail()) || n.getUsername().equals(usermodel.getEmail())) && n.getPassword().equals(usermodel.getPassword())).collect(Collectors.toList());
		
		if(verifiedUser.size() ==1) {
			return verifiedUser.get(0);
		}
		else {
			return null;
		}
		
	}

	public UserModel validateUsername(UserModel user) {
		return null;
	}

	public UserModel validatePassword(UserModel usermodel) {
		List<UserModel> user = homeRepo.findAll();
		List<UserModel> verifiedUser = user.stream().filter(n -> n.getEmail().equals(usermodel.getEmail()) && n.getSecurityQuestion().equals(usermodel.getSecurityQuestion())  && n.getSecurityAnswer().equals(usermodel.getSecurityAnswer())).collect(Collectors.toList());
		if(verifiedUser.size() ==1) {
			return verifiedUser.get(0);
	}
		else {
			return null;
		}
	}

	public void saveNewPassword(UserModel usermodel) {
		
		UserModel user = homeRepo.findbyEmail(usermodel.getEmail());
		System.out.println("user#########"+user.toString());
		user.setPassword(usermodel.getPassword());
		homeRepo.save(user);
	}
	
  
    public int sendSimpleMail(EmailModel details)
    {
        try {

        	SimpleMailMessage mailMessage
            = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(new String[]{details.getRecipient()});
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            System.out.println("------------------body"+ mailMessage.getFrom()+"recep============"+mailMessage.getTo()+"recc--++"+details.getRecipient());
            javaMailSender.send(mailMessage);
            return 1;
        }
        catch (Exception e) {
            return 0;
        }
    }

	public UserModel findUser(String email) {
		List<UserModel> user = homeRepo.findAll();
		List<UserModel> veifiedUser = user.stream().filter(n -> n.getEmail().equals(email)).collect(Collectors.toList());
		if(veifiedUser.size() > 0) {
		return veifiedUser.get(0);
		}
		else {
			return null;
		}

	}

	public UserModel findUserByUsername(String username) {

		List<UserModel> user = homeRepo.findAll();
		List<UserModel> veifiedUser = user.stream().filter(n -> n.getUsername().equals(username)).collect(Collectors.toList());
		if(veifiedUser.size() > 0) {
		return veifiedUser.get(0);
			}
			else {
				return null;
			}

	}

	public void deleteUser(Long id) {

		homeRepo.deleteById(id);

	}

	public List<UserModel> getAllStudents() {
		// TODO Auto-generated method stub
		return homeRepo.findAllStudents();
	}

	public void saveUserProfile(String age, String address, String email) {
		// TODO Auto-generated method stub
		UserModel user = homeRepo.findbyEmail(email);
		UserProfileModel userProfile = new UserProfileModel();
		userProfile.setUser(user);
		userProfile.setAddress(address);
		userProfile.setAge(age);
		userProfileRepo.save(userProfile);
	}

	public void saveUserContact(String mobileNo, String email) {
		// TODO Auto-generated method stub
		UserModel user = homeRepo.findbyEmail(email);
		userContact.setUser(user);
		userContact.setMobileNo(mobileNo);
		userContactRepo.save(userContact);

	}

	public List<Announcement> getAllAnnouncements() {
		// TODO Auto-generated method stub

		return announcementRepo.findAll();
	}

	public List<UserModel> getAllOwners() {
		// TODO Auto-generated method stub
		return homeRepo.findAllOwners();
	}



}
