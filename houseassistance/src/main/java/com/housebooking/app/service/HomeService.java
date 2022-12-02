package com.housebooking.app.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.housebooking.app.dao.AddressRepo;
import com.housebooking.app.dao.AnnouncementRepo;
import com.housebooking.app.dao.HomeRepo;
import com.housebooking.app.dao.ReviewRepo;
import com.housebooking.app.dao.UserSecurityRepo;
import com.housebooking.app.dao.UserProfileRepo;
import com.housebooking.app.model.AddressModel;
import com.housebooking.app.model.Announcement;
import com.housebooking.app.model.ReviewModel;
import com.housebooking.app.model.UserModel;
import com.housebooking.app.model.UserProfileModel;
import com.housebooking.app.model.UserSecurityModel;

import org.springframework.mail.SimpleMailMessage;

@Service
public class HomeService {
	
	@Autowired
	private HomeRepo homeRepo;
	@Autowired
	private UserProfileRepo userProfileRepo;
	@Autowired
	private UserSecurityRepo userSecurityRepo;
	@Autowired
	private AddressRepo addressRepo;
	@Autowired
	private ReviewRepo reviewsRepo;

	@Autowired
	private AnnouncementRepo announcementRepo;



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

		if(usermodel.getEmail().equals("admin@gmail.com") && usermodel.getPassword().equals("admin")) {

			usermodel.setUsertype("admin");

			return usermodel;
		}

		List<UserModel> user = homeRepo.findAll();
		List<UserModel> veifiedUser = user.stream().filter(n -> (n.getEmail().equals(usermodel.getEmail()) || n.getUsername().equals(usermodel.getEmail())) && n.getPassword().equals(usermodel.getPassword())).collect(Collectors.toList());
		
		if(veifiedUser.size() ==1) {
			return veifiedUser.get(0);
		}
		else {
			return null;
		}
		


	}

	public UserModel validateUsername(UserModel user) {
		// TODO Auto-generated method stub
		return null;
	}

	public int validatePassword(UserModel usermodel, String securityQuestion, String securityAnswer) {
		List<UserModel> user = homeRepo.findAll();
		List<UserModel> verifiedUser = user.stream().filter(n -> n.getEmail().equals(usermodel.getEmail())).collect(Collectors.toList());
		if(verifiedUser.size() ==1) {
			List<UserSecurityModel> userSecurities = userSecurityRepo.findAll();

			List<UserSecurityModel> securedUser = userSecurities.stream().filter(security -> security.getUser().equals(verifiedUser.get(0))
					&&  security.getSecurityQuestion().equals(securityQuestion) && security.getSecurityAnswer().equals(securityAnswer)

					).collect(Collectors.toList());
			if(securedUser.size() == 1) {
				return 1;
			}
			else {
				return 2;
			}
		}
		else {
			return 0;
		}
	}

	public void saveNewPassword(UserModel usermodel) {
		
		UserModel user = homeRepo.findbyEmail(usermodel.getEmail());
		System.out.println("user#########"+user.toString());
		user.setPassword(usermodel.getPassword());
		homeRepo.save(user);
	}
	
  


	public UserModel findUser(String email) {
		List<UserModel> user = homeRepo.findAll();
		System.out.println("----"+user.size());
		if(user.size() == 0) {
			return null;
		}
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
		userProfileRepo.deleteById(id);
		userSecurityRepo.deleteById(id);
		addressRepo.deleteByUserProfile(id);

	}

	public List<UserModel> getAllStudents() {
		// TODO Auto-generated method stub
		return homeRepo.findAllStudents();
	}

	public List<UserModel> getAllOwners() {
		// TODO Auto-generated method stub
		return homeRepo.findAllOwners();
	}

	public void saveUserProfile(String mobileNo,String firstname, String lastname,
			String age, String email, String doorNo, String street, String city,String zipCode) {
		// TODO Auto-generated method stub
		UserModel user = homeRepo.findbyEmail(email);
		UserProfileModel userProfile = new UserProfileModel();
		userProfile.setUser(user);
		userProfile.setMobileNo(mobileNo);
		userProfile.setFirstname(firstname);
		userProfile.setLastname(lastname);
		userProfile.setAge(age);
		UserProfileModel savedUserProfile = userProfileRepo.save(userProfile);




		AddressModel address = new AddressModel();

		address.setProfileId(savedUserProfile.getId());
		address.setCity(city);
		address.setDoorNo(doorNo);
		address.setStreet(street);
		address.setZipCode(zipCode);
		address.setHouseId(new Long(0));
		addressRepo.save(address);


	}

	public void saveUserAddress(String mobileNo,String firstname, String lastname, String age, String email) {
		// TODO Auto-generated method stub
		UserModel user = homeRepo.findbyEmail(email);
		UserProfileModel userProfile = new UserProfileModel();
		userProfile.setUser(user);
		userProfile.setMobileNo(mobileNo);
		userProfile.setFirstname(firstname);
		userProfile.setLastname(lastname);
		userProfile.setAge(age);

		userProfileRepo.save(userProfile);
	}

	public List<Announcement> getAllAnnouncements() {
		// TODO Auto-generated method stub

		return announcementRepo.findAll();
	}

	public void saveUserSecurity(String securityQuestion,String securityAnswer, String email) {
		// TODO Auto-generated method stub
		UserModel user = homeRepo.findbyEmail(email);
		UserSecurityModel userSecurity = new UserSecurityModel();
		userSecurity.setUser(user);
		userSecurity.setSecurityQuestion(securityQuestion);
		userSecurity.setSecurityAnswer(securityAnswer);
		userSecurityRepo.save(userSecurity);

	}

	public UserProfileModel getUserProfile(Long id) {
		// TODO Auto-generated method stub


		return userProfileRepo.findUserProfile(id);
	}

	public UserSecurityModel getUserSecurity(Long id) {
		// TODO Auto-generated method stub
		return userSecurityRepo.findUserSecurity(id);
	}

	public AddressModel getUserAddress(Long id) {
		// TODO Auto-generated method stub
		return addressRepo.findUserAddress(id);
	}

	public void updateUserProfile(Long id, String email, String mobileNo, String firstname, String lastname, String age) {
		// TODO Auto-generated method stub
		UserProfileModel userProfile = userProfileRepo.findUserProfile(id);
		userProfile.setFirstname(firstname);
		userProfile.setLastname(lastname);
		userProfile.setAge(age);

		userProfileRepo.save(userProfile);


	}

	public void updateUserSecurity(Long id, String securityQuestion, String securityAnswer) {
		// TODO Auto-generated method stub

		UserSecurityModel userSecurity = userSecurityRepo.findUserSecurity(id);

		userSecurity.setSecurityQuestion(securityQuestion);
		userSecurity.setSecurityAnswer(securityAnswer);

		userSecurityRepo.save(userSecurity);

	}

	public void updateUserAddress(Long id, String doorNo, String street, String city, String zipCode) {
		// TODO Auto-generated method stub

		AddressModel address = addressRepo.findUserAddress(id);

		address.setDoorNo(doorNo);
		address.setStreet(street);
		address.setCity(city);
		address.setZipCode(zipCode);

		addressRepo.save(address);



	}

	public List<ReviewModel> getAllReviews() {
		// TODO Auto-generated method stub
		return reviewsRepo.findAll();
	}







}
