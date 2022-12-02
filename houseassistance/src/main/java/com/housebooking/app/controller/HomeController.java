package com.housebooking.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.housebooking.app.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.housebooking.app.model.AddressModel;
import com.housebooking.app.model.EmailModel;
import com.housebooking.app.model.ReviewModel;
import com.housebooking.app.model.UserModel;
import com.housebooking.app.model.UserProfileModel;
import com.housebooking.app.model.UserSecurityModel;
import com.housebooking.app.service.HomeService;

@Controller
public class HomeController {

	@Autowired
	private HomeService homeService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserModel usermodel;

	@Autowired
	private EmailModel emailmodel;

	public enum UserType{
		HOME_OWNER("houseowner"),STUDENT("student"),ADMIN("admin");
		private String name;
		private UserType(String name){
			this.name = name;
		}

		public String getName()
		{
			return name;
		}

	}

	@GetMapping("/")
	public String getHome(Model model)
	{
		return "index";
	}
	
	@GetMapping("/register")
	public String getRegister(Model model)
	{
		model.addAttribute("user", usermodel);
		return "home/register";
	}

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute("user") UserModel user, @RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname,
			@RequestParam("mobileNo") String mobileNo, @RequestParam("doorNo") String doorNo, @RequestParam("street") String street,
			@RequestParam("city") String city, @RequestParam("zipCode") String zipCode,
			@RequestParam("age") String age, @RequestParam("securityQuestion") String securityQuestion, @RequestParam("securityAnswer") String securityAnswer,
			Model model)
	{
		System.out.println("save===user");


		UserModel existingUser = homeService.findUser(user.getEmail());

		if(existingUser != null) {
			model.addAttribute("errormsg", "Email already exists");
			return "home/error";
		}

		UserModel existingUsername = homeService.findUserByUsername(user.getUsername());

		if(existingUsername != null) {
			model.addAttribute("errormsg", "Username already exists");
			return "home/error";
		}




		int output = homeService.saveUser(user);
		homeService.saveUserProfile(mobileNo, firstname, lastname, age, user.getEmail(), doorNo, street, city, zipCode);
		homeService.saveUserSecurity(securityQuestion, securityAnswer, user.getEmail());

		if(output > 0) {
		return "redirect:/login";
		}
		else {
			model.addAttribute("errormsg", "Account creation failed");
			return "home/error";
		}
	}
	

	@GetMapping("/login")
	public String getLoginPage(Model model,  HttpSession session, HttpServletRequest request)
	{
		request.getSession().invalidate();
		model.addAttribute("user", usermodel);
		return "home/login";
	}
	
	@PostMapping("/authenticateLogin")
	public String loginUser(@ModelAttribute("user") UserModel user,RedirectAttributes attributes,HttpServletRequest request,HttpServletResponse response, Model model)
	{
		System.out.println("login**************************************** ");
		UserModel  userModel = homeService.authenticateUser(user);
		String username="";
		String useremail="";
		System.out.println("output=== "+userModel);
		if(userModel != null)
		{
			@SuppressWarnings("unchecked")
			List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
			if (messages == null) {
				messages = new ArrayList<>();
				request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
			}
			if(userModel.getUsertype().equals(UserType.HOME_OWNER.getName())) {
				username=userModel.getEmail().split("@")[0].toString().toUpperCase();
				useremail=userModel.getEmail();
				messages.add(useremail);
				request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
				return "redirect:/houseowner";
			} 
			else if(userModel.getUsertype().equals(UserType.STUDENT.getName())) {
				username=userModel.getEmail().split("@")[0].toString().toUpperCase();
				useremail=userModel.getEmail();
				messages.add(useremail);
				request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
				return "redirect:/user";
			}
			else {
				username=userModel.getEmail().split("@")[0].toString().toUpperCase();
				useremail=userModel.getEmail();
				messages.add(useremail);
				request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
				return "redirect:/admin";
			}
		}
		else {
			model.addAttribute("errormsg", "Login Failed. Invalid Credentials. Please try again.");
			return "home/error";
		}
		
		
	}
	
	@GetMapping("/forgotUsername")
	public String getForgotUsernamePage(Model model)
	{
		model.addAttribute("user", usermodel);
		return "home/forgotusername";
	}
	
	@GetMapping("/forgotPassword")
	public String getForgotPasswordPage(Model model)
	{
		model.addAttribute("user", usermodel);
		return "home/forgotpassword";
	}
	
	
	@PostMapping("/sendMail")
	public String sendMail(@ModelAttribute("user") UserModel user, Model model)
	{	
		int output = 0;
		System.out.println("save===usernew password");
		System.out.println("userModel#########"+user.toString());
		UserModel userModel=homeService.findUser(user.getEmail());

		if(userModel == null) {
			model.addAttribute("errormsg", "Email Id doesnot exist in our database");
			return "home/error";
		}
		emailmodel.setMsgBody("Your Username is "+ userModel.getUsername());
		emailmodel.setRecipient(userModel.getEmail());
		emailmodel.setSubject("Username Recovery from HouseAssistance");
		System.out.println("------------------body"+ emailmodel.getMsgBody()+"======="+ emailmodel.getRecipient());
		output = messageService.sendSimpleMail(emailmodel);
		
		System.out.println("------------------"+ output);
		if(output !=1) {
			model.addAttribute("errmsg", "User Email address not found.");
		}
		return "redirect:/login";
	}
	
	@PostMapping("/validateForgotPassword")
	public String validatePassword(@ModelAttribute("user") UserModel user, @RequestParam("securityQuestion") String securityQuestion,
			 @RequestParam("securityAnswer") String securityAnswer,
			Model model,RedirectAttributes redirectAttrs)
	{
		System.out.println("forgot password**************************************** ");
		int output = homeService.validatePassword(user, securityQuestion, securityAnswer);

		if(output == 1)
		{

			return "home/changepassword";
		}
		else if(output == 0) {
			model.addAttribute("errormsg", "Invalid User Email");
			return "home/error";
		}
		else if(output == 2) {
			model.addAttribute("errormsg", "Invalid Security Question or Answer");
			return "home/error";
		}
		else {
			model.addAttribute("errormsg", "Password cannot be changed. Invalid credentials.");
			return "home/error";
		}
		
		
	}
	
	@GetMapping("/changePassword")
	public String getChangePasswordPage(Model model)
	{
		model.addAttribute("user", usermodel);
		return "home/changepassword";
	}
	
	@PostMapping("/saveNewPassword")
	public String saveNewPassword(@ModelAttribute("user") UserModel user, HttpServletRequest request, @Param("confirmPassword") String confirmPassword, Model model)
	{
		if(confirmPassword.equals(user.getPassword())) {

			homeService.saveNewPassword(user);
		}
		else {
			model.addAttribute("errormsg", "Passwords donot match");
			return "home/error";
		}
		System.out.println("save===usernew password");
		System.out.println("userModel#########"+user.toString());
		 request.getSession().invalidate();
		return "redirect:/login";
	}
	
	@RequestMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
	
	@RequestMapping("/profile")
    public String viewProfile(HttpSession session, Model model) {
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		UserProfileModel userProfile = homeService.getUserProfile(userdata.getId());
		UserSecurityModel userSecurity =  homeService.getUserSecurity(userdata.getId());
		AddressModel userAddress = homeService.getUserAddress(userProfile.getId());
		model.addAttribute("role", userdata.getUsertype());
		model.addAttribute("user", userdata);
		model.addAttribute("userProfile", userProfile);
		model.addAttribute("userSecurity", userSecurity);
		model.addAttribute("userAddress", userAddress);
        return "home/profile";
    }
	
	@PostMapping("/updateProfile")
	public String updateProfile(@ModelAttribute("user") UserModel user, @RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname,
			@RequestParam("mobileNo") String mobileNo, @RequestParam("doorNo") String doorNo, @RequestParam("street") String street,
			@RequestParam("city") String city, @RequestParam("zipCode") String zipCode,
			@RequestParam("age") String age, @RequestParam("securityQuestion") String securityQuestion, @RequestParam("securityAnswer") String securityAnswer, Model model)
	{
		System.out.println("save===user");
		int output =homeService.saveUser(user);

		homeService.updateUserAddress(user.getId(), doorNo, street, city, zipCode);
		homeService.updateUserProfile(user.getId(), user.getEmail(), mobileNo,firstname, lastname, age);
		homeService.updateUserSecurity(user.getId(), securityQuestion, securityAnswer);

		if(output>0) {
			return "redirect:/profile";
		}

		else {
			model.addAttribute("errormsg", "Operation failed. Please try again");
			return "home/error";
		}

	}
	
	@PostMapping("/deleteProfile/{id}")
	public String deleteProfile(@PathVariable(name="id") Long id,HttpServletRequest request, Model model)
	{
		homeService.deleteUser(id);
		 request.getSession().invalidate();
		 model.addAttribute("errormsg", "Your Account Deleted Successfully");
			return "home/error";
	}
	
	@GetMapping("/resetPassword")
	public String resetPassword(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		UserModel userdata = homeService.findUser(messages.get(0));
		
		model.addAttribute("user", userdata);
		
		return "home/resetpassword";



	}
	
	@GetMapping("/appReviews")
	public String appReviews(Model model, HttpSession session) {

		List<ReviewModel> reviews = homeService.getAllReviews();

		model.addAttribute("reviews", reviews);

		return "home/appreviews";



	}



}
