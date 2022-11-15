package com.housebooking.app.controller;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.housebooking.app.model.*;
import com.housebooking.app.service.AdminService;
import com.housebooking.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.housebooking.app.service.HomeService;
import com.housebooking.app.service.HouseOwnerService;
import com.housebooking.app.utils.FileUploadUtil;

@Controller
public class HouseOwnerController {
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private HouseOwnerService houseOwnerService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserService userService;

	@Autowired
	private ReviewModel review;

	@Autowired
	private HouseModel house;

	@Autowired
	private ReportModel report;

	@Autowired
	private TicketModel ticket;
	
	@GetMapping("/houseowner")
	public String getHouseOwnerWelcomePage(@ModelAttribute("user") UserModel user, Model model, HttpSession session)
	{
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
        UserModel userdata = homeService.findUser(messages.get(0));
        model.addAttribute("role", userdata.getUsertype());
		return "houseowner/welcomehouseowner";
	}
	
	@GetMapping("/createHouse")
	public String createHouse(Model model, HttpSession session) {

		model.addAttribute("house", house);
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

        if (messages == null) {
            messages = new ArrayList<>();
        }
        model.addAttribute("sessionMessages", messages);
        UserModel userdata = homeService.findUser(messages.get(0));
        model.addAttribute("role", userdata.getUsertype());
		return "houseowner/createhouse";
	}
	
	@PostMapping("/saveHouse")
	public String saveHouseBooking(@ModelAttribute("house") HouseModel house, @RequestParam("image") MultipartFile multipartFile
			, @RequestParam("doc") MultipartFile doc, Model model, HttpSession session)
	{
		System.out.println("house created");
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
        UserModel userdata = homeService.findUser(messages.get(0));
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		try {
			house.setHousePhoto(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
			house.setDocument(Base64.getEncoder().encodeToString(doc.getBytes()));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		house.setHouseOwnerMail(userdata.getEmail());
		house.setIsBooked("0");
		house.setIsHide("0");
		house.setLikes(0);
		house.setDislikes(0);
		houseOwnerService.saveHouse(house);
		
		return "redirect:/houseowner";
	}
	
	@GetMapping("/viewHouses")
	public String viewHouses(Model model, HttpSession session) {
		
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		List<HouseModel> houses = houseOwnerService.getAllHousesByEmail(userdata.getEmail());
		model.addAttribute("houses", houses);
		model.addAttribute("role", userdata.getUsertype());
		return "houseowner/displayhouses";
	}
	
	@GetMapping("/editHouse/{id}")
	public String editHouse(Model model, HttpSession session, @PathVariable(name="id") Long id) {
		
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		HouseModel house = houseOwnerService.getHouseById(id);
		System.out.println(house.getHouseName());
		model.addAttribute("house", house);
		model.addAttribute("role", userdata.getUsertype());

		return "houseowner/updatehouse";
	}
	
	@PostMapping("/updateHouse")
	public String updateHouse(@ModelAttribute("house") HouseModel house, @RequestParam("image") MultipartFile multipartFile)
	{
		System.out.println("house updated");
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		try {
			house.setHousePhoto(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		houseOwnerService.saveHouse(house);
		
		return "redirect:/houseowner";
	}
	
	@PostMapping("/deleteHouse/{id}")
	public String deleteHouse(@PathVariable(name="id") Long id)
	{
		houseOwnerService.deleteHouse(id);
		
		return "redirect:/houseowner";
	}
	
	@GetMapping("/reportStudent")
	public String reportStudent(Model model, HttpSession session) {
		
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		List<UserModel> students = homeService.getAllStudents();
		model.addAttribute("students", students);
		model.addAttribute("report", report);
		model.addAttribute("role", userdata.getUsertype());

		return "houseowner/reportstudent";
	}
	
	@PostMapping("/report")
	public String report(@ModelAttribute("report") ReportModel report)
	{
		System.out.println("reported student");
		
		UserModel userdata = homeService.findUser(report.getUserMail());
		report.setUserType(userdata.getUsertype());
		houseOwnerService.saveReport(report);
		
		return "redirect:/houseowner";
	}
	
	@GetMapping("/review")
	public String review(Model model, HttpSession session) {
		
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		model.addAttribute("review", review);
		model.addAttribute("usermail", userdata.getEmail());
		model.addAttribute("role", userdata.getUsertype());

		return "houseowner/reviewapp";
	}
	
	@PostMapping("/reviewApp")
	public String reviewApp(@ModelAttribute("review") ReviewModel review, Model model, HttpSession session)
	{
		System.out.println("reviewed App");
		

		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		review.setUserMail(userdata.getEmail());
		houseOwnerService.saveReview(review);
		
		return "redirect:/houseowner";
	}

	@GetMapping("/ticket")
	public String ticket(Model model, HttpSession session) {


		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		model.addAttribute("ticket", ticket);
		model.addAttribute("userMail", userdata.getEmail());
		model.addAttribute("role", userdata.getUsertype());

		return "houseowner/raiseticket";
	}

	@PostMapping("/raiseTicket")
	public String raiseTicket(@ModelAttribute("ticket") TicketModel ticket, HttpSession session, Model model )
	{
		System.out.println("raised Ticket");

		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		ticket.setUserMail(userdata.getEmail());

		houseOwnerService.saveTicket(ticket);

		return "redirect:/houseowner";
	}

	@GetMapping("/notifications")
	public String notifications(Model model, HttpSession session) {


		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));

		List<Announcement> notifications = homeService.getAllAnnouncements();
		model.addAttribute("notifications", notifications);
		model.addAttribute("role", userdata.getUsertype());
		return "houseowner/viewnotifications";
	}

	@GetMapping("/viewFaqsHO")
	public String viewFaqsPage(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		List<FAQModel> faqs = adminService.findAllFAQs();
		model.addAttribute("faqs", faqs);
		return "houseowner/viewfaqs";

	}

	@GetMapping("/viewMessages")
	public String viewMessagesPage(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		List<MessageModel> msgs = houseOwnerService.findAllMessages(userdata.getEmail());
		model.addAttribute("msgs", msgs);
		return "houseowner/viewmsgs";

	}

	@GetMapping("/replyMsgPage/{id}")
	public String replyMsgPage(Model model, HttpSession session, @PathVariable(name="id") Long id) {


		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));

		MessageModel msg = houseOwnerService.getMsgById(id);
		model.addAttribute("msg", msg);
		model.addAttribute("role", userdata.getUsertype());

		return "houseowner/replymessage";
	}

	@PostMapping("/replyMsg")
	public String raiseTicket(@ModelAttribute("msg") MessageModel msg, HttpSession session, Model model )
	{
		System.out.println("raised Ticket");

		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));


		userService.saveMsg(msg);

		return "redirect:/houseowner";
	}
}
