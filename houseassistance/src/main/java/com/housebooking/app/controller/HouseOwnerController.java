package com.housebooking.app.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpSession;

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

import com.housebooking.app.model.AddressModel;
import com.housebooking.app.model.Announcement;
import com.housebooking.app.model.AppointmentModel;
import com.housebooking.app.model.BookModel;
import com.housebooking.app.model.FAQModel;
import com.housebooking.app.model.HouseAttributesModel;
import com.housebooking.app.model.HouseDetailsModel;
import com.housebooking.app.model.HouseModel;
import com.housebooking.app.model.MessageModel;
import com.housebooking.app.model.ReportModel;
import com.housebooking.app.model.ReviewModel;
import com.housebooking.app.model.ReviewOwnerModel;
import com.housebooking.app.model.ReviewPropertyModel;
import com.housebooking.app.model.TicketModel;
import com.housebooking.app.model.UserModel;
import com.housebooking.app.model.UserProfileModel;
import com.housebooking.app.service.AdminService;
import com.housebooking.app.service.CommonService;
import com.housebooking.app.service.HomeService;
import com.housebooking.app.service.HouseOwnerService;
import com.housebooking.app.service.UserService;

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
	private CommonService commonService;

	@Autowired
	private HouseModel house;

	@Autowired
	private ReportModel report;

	@Autowired
	private ReviewModel review;

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
	public String saveHouse(@ModelAttribute("house") HouseModel house, @RequestParam("image") MultipartFile houseImage,
			@RequestParam("houseName") String houseName, @RequestParam("houseRent") String houseRent, @RequestParam("noOfBedrooms") String noOfBedrooms,
			@RequestParam("noOfBathrooms") String noOfBathrooms,
			@RequestParam("doorNo") String doorNo, @RequestParam("street") String street, @RequestParam("city") String city,
			@RequestParam("zipCode") String zipCode, @RequestParam("parking") String parking, @RequestParam("petFriendly") String petFriendly,
			@RequestParam("lawn") String lawn, @RequestParam("availableFrom") String availableFrom,
			@RequestParam("doc") MultipartFile doc, Model model, HttpSession session)
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

        UserProfileModel userProfile = homeService.getUserProfile(userdata.getId());
		

		houseOwnerService.saveHouse(house, userdata.getEmail(), houseImage, doc, houseName,
				houseRent, userProfile.getMobileNo(), noOfBedrooms, noOfBathrooms, doorNo, street, city, zipCode, parking, petFriendly, lawn, availableFrom);
		
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
		try {
			UserModel userdata = homeService.findUser(messages.get(0));
			List<HouseDetailsModel> houses = houseOwnerService.getAllHousesDetailsByEmail(userdata.getEmail());
			model.addAttribute("houses", houses);
			model.addAttribute("houseOwnerMail", userdata.getEmail());
			model.addAttribute("role", userdata.getUsertype());
		}
		catch(Exception e)
		{
			model.addAttribute("errormsg", "Unable to view Houses. Try again after sometime");
			return "home/error";
		}
		return "houseowner/displayhouses";
	}
	
	@GetMapping("/viewSingleHouse/{id}")
	public String viewSingleHouse(Model model, HttpSession session, @PathVariable(name="id") Long id) {


		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		try {
			UserModel userdata = homeService.findUser(messages.get(0));
			HouseModel house = houseOwnerService.getHouseById(id);

			HouseDetailsModel houseDetails = houseOwnerService.getHouseDetailsById(id);
			HouseAttributesModel houseAttributes = houseOwnerService.getHouseAttributes(id);
			AddressModel houseAddress = houseOwnerService.getHouseAddress(id);

			model.addAttribute("house", house);
			model.addAttribute("houseDetails", houseDetails);
			model.addAttribute("houseAttributes", houseAttributes);
			model.addAttribute("houseAddress", houseAddress);

			model.addAttribute("role", userdata.getUsertype());
		}
		catch(Exception e)
		{
			model.addAttribute("errormsg", "Unable to view house. Try again after sometime");
			return "home/error";
		}
		return "houseowner/displaysinglehouse";
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

		HouseDetailsModel houseDetails = houseOwnerService.getHouseDetailsById(id);
		HouseAttributesModel houseAttributes = houseOwnerService.getHouseAttributes(id);
		AddressModel houseAddress = houseOwnerService.getHouseAddress(id);

		model.addAttribute("house", house);
		model.addAttribute("houseDetails", houseDetails);
		model.addAttribute("houseAttributes", houseAttributes);
		model.addAttribute("houseAddress", houseAddress);
		model.addAttribute("role", userdata.getUsertype());

		return "houseowner/updatehouse";
	}
	
	@PostMapping("/updateHouse")
	public String updateHouse(@ModelAttribute("house") HouseModel house, @RequestParam("image") MultipartFile houseImage,
			@RequestParam("houseName") String houseName, @RequestParam("houseRent") String houseRent, @RequestParam("noOfBedrooms") String noOfBedrooms,
			@RequestParam("noOfBathrooms") String noOfBathrooms,
			@RequestParam("doorNo") String doorNo, @RequestParam("street") String street, @RequestParam("city") String city,
			@RequestParam("zipCode") String zipCode, @RequestParam("parking") String parking, @RequestParam("petFriendly") String petFriendly,
			@RequestParam("lawn") String lawn, @RequestParam("isHide") String isHide, @RequestParam("isBooked") String isBooked,
			@RequestParam("availableFrom") String availableFrom,
			@RequestParam("doc") MultipartFile doc, Model model, HttpSession session)
	{
		System.out.println("house updated");
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));

		UserProfileModel userProfile = homeService.getUserProfile(userdata.getId());

		
		houseOwnerService.updateHouse(house, userdata.getEmail(), houseImage, doc, houseName,
				houseRent, userProfile.getMobileNo(), noOfBedrooms, noOfBathrooms, doorNo, street,
				city, zipCode, parking, petFriendly, lawn, isHide, isBooked, availableFrom);

		
		return "redirect:/houseowner";
	}
	
	@PostMapping("/deleteHouse/{id}")
	public String deleteHouse(@PathVariable(name="id") Long id)
	{
		houseOwnerService.deleteHouse(id);
		
		return "redirect:/houseowner";
	}
	

	@GetMapping("/viewAppointments")
	public String viewAppointments(Model model, HttpSession session)
	{
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		try {
			model.addAttribute("sessionMessages", messages);
			UserModel userdata = homeService.findUser(messages.get(0));
			model.addAttribute("role", userdata.getUsertype());

			List<AppointmentModel> appointments = userService.getAllAppointmentsByUserId(userdata.getEmail());

			model.addAttribute("appointments", appointments);
		}
		catch(Exception e)
		{
			model.addAttribute("errormsg", "Unable to view appointments. Try again after sometime");
			return "home/error";
		}
		return "houseowner/viewappointments";
	}

	@GetMapping("/reportStudent")
	public String reportStudent(Model model, HttpSession session) {


		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if (messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		try
		{
		UserModel userdata = homeService.findUser(messages.get(0));
		List<UserModel> students = homeService.getAllStudents();
		model.addAttribute("students", students);
		model.addAttribute("report", report);
		model.addAttribute("role", userdata.getUsertype());
	}
	catch(Exception e)
	{
		model.addAttribute("errormsg", "Unable to report student. Try again after sometime");
		return "home/error";
	}

		return "houseowner/reportstudent";
	}
	
	@PostMapping("/report")
	public String report(@ModelAttribute("report") ReportModel report, Model model)
	{
		System.out.println(report.getUserMail());
		
		if(report.getUserMail().equals("NA")) {
			model.addAttribute("errormsg", "Please Select User Email");
			return "home/error";
		}
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
		try {
			UserModel userdata = homeService.findUser(messages.get(0));
			model.addAttribute("review", review);
			model.addAttribute("usermail", userdata.getEmail());
			model.addAttribute("role", userdata.getUsertype());
		}
		catch(Exception e)
		{
			model.addAttribute("errormsg", "Unable to get review. Try again after sometime");
			return "home/error";
		}

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
		commonService.saveReview(review);
		
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
		try {
			UserModel userdata = homeService.findUser(messages.get(0));
			model.addAttribute("ticket", ticket);
			model.addAttribute("userMail", userdata.getEmail());
			model.addAttribute("role", userdata.getUsertype());
		}
		catch(Exception e)
		{
			model.addAttribute("errormsg", "Unable to view tickets. Try again after sometime");
			return "home/error";
		}

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

		commonService.saveTicket(ticket);

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
		try {
			UserModel userdata = homeService.findUser(messages.get(0));
			List<FAQModel> faqs = adminService.findAllFAQs();
			model.addAttribute("faqs", faqs);
		}
		catch(Exception e)
		{
			model.addAttribute("errormsg", "Unable to view FAQ's. Try again after sometime");
			return "home/error";
		}
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
		try {
			UserModel userdata = homeService.findUser(messages.get(0));
			List<MessageModel> msgs = houseOwnerService.findAllMessages(userdata.getEmail());
			model.addAttribute("msgs", msgs);
		}
		catch(Exception e)
		{
			model.addAttribute("errormsg", "Unable to view messages. Try again after sometime");
			return "home/error";
		}
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
		try {
			UserModel userdata = homeService.findUser(messages.get(0));

			MessageModel msg = houseOwnerService.getMsgById(id);
			model.addAttribute("msg", msg);
			model.addAttribute("role", userdata.getUsertype());
		}
		catch(Exception e)
		{
			model.addAttribute("errormsg", "Unable to reply message. Try again after sometime");
			return "home/error";
		}

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

	@GetMapping("/myreviews")
	public String myReviews(Model model, HttpSession session) {


		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		try {
			List<ReviewOwnerModel> myReviews = houseOwnerService.getAllMyReviews(userdata.getEmail());
			model.addAttribute("myReviews", myReviews);
			model.addAttribute("role", userdata.getUsertype());
		}
		catch(Exception e)
		{
			model.addAttribute("errormsg", "Unable to show reviews. Try again after sometime");
			return "home/error";
		}
		return "houseowner/viewownerreviews";
	}

	@GetMapping("/propertyreviews")
	public String propertyReviews(Model model, HttpSession session) {


		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		try {
			UserModel userdata = homeService.findUser(messages.get(0));

			List<ReviewPropertyModel> reviews = houseOwnerService.getAllPropertyReviews(userdata.getEmail());
			model.addAttribute("reviews", reviews);
			model.addAttribute("role", userdata.getUsertype());
		}
		catch(Exception e)
		{
			model.addAttribute("errormsg", "Unable to property reviews. Try again after sometime");
			return "home/error";
		}
		return "houseowner/viewpropertyreviews";
	}
}
