package com.housebooking.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.housebooking.app.model.EmailModel;
import com.housebooking.app.model.UserModel;
import com.housebooking.app.service.HomeService;

@Controller
public class HomeController {
	
	@Autowired
	private HomeService homeService;
	
	@GetMapping("/")
	public String getHome(Model model)
	{
		return "index";
	}
	
	@GetMapping("/register")
	public String getRegister(Model model)
	{
		
		UserModel usermodel = new UserModel();
		model.addAttribute("user", usermodel);
		return "home/register";
	}
	
	@PostMapping("/saveUser")
	public String saveHouseBooking(@ModelAttribute("user") UserModel user)
	{
		System.out.println("save===user");
		user.setUsername(user.getFirstname()+user.getLastname());
		homeService.saveUser(user);
		return "redirect:/login";
	}
	
	
	@GetMapping("/login")
	public String getLoginPage(Model model,  HttpSession session)
	{	
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if (messages == null) {
			messages = new ArrayList<>();
		}
		model.addAttribute("sessionMessages", messages);
		UserModel usermodel = new UserModel();
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
			if(userModel.getUsertype().equals("houseowner")) {
				username=userModel.getEmail().split("@")[0].toString().toUpperCase();
				useremail=userModel.getEmail();
				messages.add(useremail);
				request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
				return "redirect:/houseowner";
			} 
			else if(userModel.getUsertype().equals("student")) {
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
			model.addAttribute("errormsg", "Invalid Login Credentials. Please try again.");
			return "home/error";
		}
		
		
	}
	
	@GetMapping("/forgotUsername")
	public String getForgotUsernamePage(Model model)
	{
		UserModel usermodel = new UserModel();
		model.addAttribute("user", usermodel);
		return "home/forgotusername";
	}
	
	@GetMapping("/forgotPassword")
	public String getForgotPasswordPage(Model model)
	{
		UserModel usermodel = new UserModel();
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
		EmailModel emailmodel = new EmailModel();
		emailmodel.setMsgBody("Your Username is"+ userModel.getFirstname()+" "+userModel.getLastname());
		emailmodel.setRecipient(userModel.getEmail());
		emailmodel.setSubject("Username Recovery from HouseAssistance");
		System.out.println("------------------body"+ emailmodel.getMsgBody()+"======="+ emailmodel.getRecipient());
		output = homeService.sendSimpleMail(emailmodel);
		
		System.out.println("------------------"+ output);
		if(output !=1) {
			model.addAttribute("errmsg", "Email address does not exist.");
		}
		return "redirect:/login";
	}
	
	@PostMapping("/validateForgotPassword")
	public String validatePassword(@ModelAttribute("user") UserModel user, Model model,RedirectAttributes redirectAttrs)
	{
		System.out.println("forgot password**************************************** ");
		String email="";
		UserModel  userModel = homeService.validatePassword(user);
		System.out.println("output=== "+userModel);
		if(userModel != null)
		{
			email=userModel.getEmail();
			System.out.println("email ***** "+email);
			System.out.println(userModel);
//			UserModel usermodel = new UserModel();
			model.addAttribute("user", userModel);
			model.addAttribute("email",email);
//			redirectAttrs.addFlashAttribute("email", email);
//			return "redirect:/changePassword";
			return "home/changepassword";
		}
		else {
			model.addAttribute("errormsg", "Invalid Login Credentials. Please try again.");
			return "home/error";
		}
		
		
	}
	
	@GetMapping("/changePassword")
	public String getChangePasswordPage(Model model)
	{
		UserModel usermodel = new UserModel();
		model.addAttribute("user", usermodel);
		return "home/changepassword";
	}
	
	@PostMapping("/saveNewPassword")
	public String saveNewPassword(@ModelAttribute("user") UserModel user, HttpServletRequest request)
	{
		System.out.println("save===usernew password");
		System.out.println("userModel#########"+user.toString());
		homeService.saveNewPassword(user);
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
		model.addAttribute("user", userdata);
        return "home/profile";
    }
	
	@PostMapping("/updateProfile")
	public String updateProfile(@ModelAttribute("user") UserModel user)
	{
		System.out.println("save===user");
		homeService.saveUser(user);
		return "redirect:/profile";
	}
	
	@PostMapping("/deleteProfile/{id}")
	public String deleteProfile(@PathVariable(name="id") Long id,HttpServletRequest request)
	{
		homeService.deleteUser(id);
		 request.getSession().invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/resetPassword")
	public String resetPassword(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		UserModel userdata = homeService.findUser(messages.get(0));
		
		model.addAttribute("user", userdata);
		
		return "home/resetpassword";
		
		
		
	}
	


}
