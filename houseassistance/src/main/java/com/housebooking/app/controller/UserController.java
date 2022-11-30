package com.housebooking.app.controller;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.housebooking.app.model.AddressModel;
import com.housebooking.app.model.Announcement;
import com.housebooking.app.model.AppointmentModel;
import com.housebooking.app.model.FAQModel;
import com.housebooking.app.model.FavouritesModel;
import com.housebooking.app.model.HouseAttributesModel;
import com.housebooking.app.model.HouseDetailsModel;
import com.housebooking.app.model.HouseModel;
import com.housebooking.app.model.MessageModel;
import com.housebooking.app.model.ReportModel;
import com.housebooking.app.model.BookModel;
import com.housebooking.app.model.ReviewModel;
import com.housebooking.app.model.ReviewOwnerModel;
import com.housebooking.app.model.ReviewPropertyModel;
import com.housebooking.app.model.TicketModel;
import com.housebooking.app.model.UserModel;
import com.housebooking.app.service.AdminService;
import com.housebooking.app.service.HomeService;
import com.housebooking.app.service.HouseOwnerService;
import com.housebooking.app.service.UserService;



@Controller
public class UserController {
	@Autowired
	private HomeService homeService;

	@Autowired
	private UserService userService;

	@Autowired
	private HouseOwnerService houseOwnerService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private BookModel book;

	@Autowired
	private AppointmentModel appointment;

	@Autowired
	private FavouritesModel favourite;

	@Autowired
	private ReportModel report;

	@Autowired
	private ReviewModel review;

	@Autowired
	private TicketModel ticket;

	@Autowired
	private MessageModel msg;


	@GetMapping("/user")
	public String getUserWelcomePage(@ModelAttribute("user") UserModel user, Model model, HttpSession session)
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
        List<HouseDetailsModel> houses = userService.getAllHouseDetails();
        model.addAttribute("houses", houses);
		return "user/welcomeuser";
	}

	@GetMapping("/viewHouse/{id}")
	public String viewHouse(Model model, HttpSession session, @PathVariable(name="id") Long id) {


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

		int isliked = userService.getIsLikedByUser(id, userdata.getId());


		int isdisliked = userService.getIsDisLikedByUser(id, userdata.getId());

		model.addAttribute("isliked", isliked);

		model.addAttribute("isdisliked", isdisliked);

		model.addAttribute("role", userdata.getUsertype());
		return "user/viewsinglehouse";
	}

	@GetMapping("/bookHouse/{id}")
	public String bookHouse(@PathVariable(name="id") Long id, Model model, HttpSession session)
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
        HouseDetailsModel houseDetails = houseOwnerService.getHouseDetailsById(id);
        model.addAttribute("houseid", id);
        model.addAttribute("houseRent", houseDetails.getHouseRent());
        model.addAttribute("book", book);
		return "user/bookhouse";
	}

	@GetMapping("/reserveHouse/{id}")
	public String reserveHouse(@PathVariable(name="id") Long id, Model model, HttpSession session)
	{
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);

        UserModel userdata = homeService.findUser(messages.get(0));

        userService.reserveHouse(id, userdata.getId());
		return "redirect:/user";
	}

	@GetMapping("/appointment/{id}")
	public String appointment(@PathVariable(name="id") Long id, Model model, HttpSession session)
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
        model.addAttribute("houseid", id);
        model.addAttribute("appointment", appointment);
		return "user/appointment";
	}

	@PostMapping("/bookAppointment")
	public String bookAppointment(@Param("houseid") Long houseid,@ModelAttribute("appointment") AppointmentModel appointment, Model model, HttpSession session) {

		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
        UserModel userdata = homeService.findUser(messages.get(0));

        HouseModel house = houseOwnerService.getHouseById(houseid);

        appointment.setHouseId(houseid.toString());
//        appointment.setHouseDetails(house.getHouseDetails());

        appointment.setUserId(userdata.getId().toString());

        userService.saveAppointment(appointment);
        return "redirect:/user";

	}

	@GetMapping("/previousBookings")
	public String previousBookings(Model model, HttpSession session)
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

        List<BookModel> bookings = userService.getAllBookingsByUserId(userdata.getId());

        model.addAttribute("bookings", bookings);
		return "user/previousbookings";
	}

	@PostMapping("/bookHouse")
	public String bookHouse( HttpSession session, Model model,
			@ModelAttribute("book") BookModel book,
			@RequestParam("doc1") MultipartFile file1,
			@RequestParam("doc2") MultipartFile file2,
			@RequestParam("houseid") String houseid) {
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
        UserModel userdata = homeService.findUser(messages.get(0));

        book.setHouseId(houseid);
        book.setUserId(userdata.getId().toString());

        String rent = book.getHouseRent();
        try {
        	book.setDocument1(Base64.getEncoder().encodeToString(file1.getBytes()));
        	book.setDocument2(Base64.getEncoder().encodeToString(file2.getBytes()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        BookModel result = userService.saveBookHouse(book);

        if(result == null) {
        	model.addAttribute("errormsg", "Coupon Not Found, Please Try Booking Again");
			return "home/error";
        }

        if(result.getHouseRent().equals(rent))
        {
        	model.addAttribute("errormsg", "Coupoun Not Applied House Rent is " + result.getHouseRent() + "$");
			return "home/error";
        } else {
        	model.addAttribute("errormsg", "Coupoun Applied Successfully House Rent is " + result.getHouseRent() + "$");
			return "home/error";
        }


	}

	@PostMapping("addToFavourites/{id}")
	public String addToFavourites(@PathVariable(name="id") String id, Model model, HttpSession session) {

		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
        UserModel userdata = homeService.findUser(messages.get(0));

        favourite.setHouseId(id);
        favourite.setUserId(userdata.getId().toString());

        userService.savefavourites(favourite);
        return "redirect:/user";

	}

	@GetMapping("/likeHouse/{id}")
	public String likeHouse(@PathVariable(name="id") Long id, Model model, HttpSession session)
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

        userService.likeHouse(id, userdata.getId());

		return "redirect:/user";
	}

	@GetMapping("/disLikeHouse/{id}")
	public String disLikeHouse(@PathVariable(name="id") Long id, Model model, HttpSession session)
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

        userService.disLikeHouse(id, userdata.getId());

		return "redirect:/user";
	}

	@GetMapping("/reportOwner")
	public String reportOwner(Model model, HttpSession session) {


		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		List<UserModel> owners = homeService.getAllOwners();
		model.addAttribute("owners", owners);
		model.addAttribute("report", report);
		List<HouseDetailsModel> houses = userService.getAllHouseDetails();
		model.addAttribute("houses", houses);
		model.addAttribute("role", userdata.getUsertype());

		return "user/reportowner";
	}

	@PostMapping("/reportOwnerAndHouse")
	public String report(@ModelAttribute("report") ReportModel report, Model model, HttpSession session)
	{
		System.out.println("reported Owner");

		UserModel user = homeService.findUser(report.getUserMail());
		report.setUserType(user.getUsertype());
		houseOwnerService.saveReport(report);

		return "redirect:/user";
	}

	@GetMapping("/reviewOwner")
	public String reviewOwner(Model model, HttpSession session) {


		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		List<UserModel> owners = homeService.getAllOwners();
		model.addAttribute("owners", owners);
		ReviewOwnerModel review = new ReviewOwnerModel();
		model.addAttribute("review", review);
		List<HouseDetailsModel> houses = userService.getAllHouseDetails();
		model.addAttribute("houses", houses);
		model.addAttribute("role", userdata.getUsertype());

		return "user/reviewowner";
	}

	@PostMapping("/reviewOwnerAndHouse")
	public String reviewOwner(@ModelAttribute("review") ReviewOwnerModel review, Model model, HttpSession session)
	{
		System.out.println("reported Owner");

		UserModel user = homeService.findUser(review.getOwnerMail());
		userService.saveReviewOwner(review);

		return "redirect:/user";
	}

	@GetMapping("/reviewProperty")
	public String reviewProperty(Model model, HttpSession session) {


		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		List<UserModel> owners = homeService.getAllOwners();
		model.addAttribute("owners", owners);
		ReviewPropertyModel property = new ReviewPropertyModel();
		model.addAttribute("property", property);
		List<HouseDetailsModel> houses = userService.getAllHouseDetails();
		model.addAttribute("houses", houses);
		model.addAttribute("role", userdata.getUsertype());

		return "user/reviewproperty";
	}

	@PostMapping("/reviewHouseProperty")
	public String reviewHouseProperty(@ModelAttribute("property") ReviewPropertyModel property, Model model, HttpSession session)
	{
		System.out.println("reported Owner");


		userService.saveReviewProperty(property);

		return "redirect:/user";
	}

	@GetMapping("/reviewapp")
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

		return "user/reviewapp";
	}

	@PostMapping("/reviewApplication")
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

		return "redirect:/user";
	}

	@GetMapping("/ticketraise")
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

		return "user/raiseticket";
	}

	@PostMapping("/ticketRaise")
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

		return "redirect:/user";
	}

	@GetMapping("/usernotifications")
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
		return "user/viewnotifications";
	}

	@GetMapping("/viewFaqsStudent")
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
		model.addAttribute("role", userdata.getUsertype());
		return "user/viewfaqs";

	}

	@GetMapping("/viewFavourites")
	public String viewFavourites(Model model, HttpSession session) {

		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		List<HouseDetailsModel> favs = userService.findAllFavs(userdata.getId());
		model.addAttribute("favs", favs);
		model.addAttribute("role", userdata.getUsertype());
		return "user/viewfavourites";

	}

	@GetMapping("/sendMessage")
	public String viewMessagePage(Model model, HttpSession session) {

		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		List<UserModel> owners = homeService.getAllOwners();
		model.addAttribute("msg", msg);
		model.addAttribute("owners", owners);
		model.addAttribute("role", userdata.getUsertype());
		return "user/sendmsg";

	}

	@PostMapping("/sendMsg")
	public String saveMessage(@ModelAttribute("msg") MessageModel msg, HttpSession session, Model model )
	{
		System.out.println("raised Ticket");

		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		msg.setStudentMail(userdata.getEmail());
		msg.setAnswer("");

		userService.saveMsg(msg);

		return "redirect:/user";
	}

	@GetMapping("/viewReplies")
	public String viewMessagesPage(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		List<MessageModel> msgs = userService.findAllMessages(userdata.getEmail());
		model.addAttribute("msgs", msgs);
		model.addAttribute("role", userdata.getUsertype());
		return "user/viewmessages";

	}

	@PostMapping("/searchHouse")
	public String searchHouse(Model model, HttpSession session, @RequestParam("searchKey") String searchKey ) {
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
        model.addAttribute("sessionMessages", messages);
        List<HouseDetailsModel> houses = userService.searchHouses(searchKey);
        model.addAttribute("houses", houses);
        model.addAttribute("role", userdata.getUsertype());
		return "user/welcomeuser";
	}

	@GetMapping("/filter")
	public String viewFiltersPage(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		model.addAttribute("role", userdata.getUsertype());
		List<HouseDetailsModel> houses = userService.getAllHouseDetails();
	    model.addAttribute("houses", houses);
		return "user/filter";

	}

	@GetMapping("/advancedFilter")
	public String viewAdvanceFiltersPage(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		model.addAttribute("role", userdata.getUsertype());
		List<HouseDetailsModel> houses = userService.getAllHouseDetails();
	    model.addAttribute("houses", houses);
		return "user/advancefilter";

	}

	@GetMapping("/sortByPrice")
	public String sortByPricePage(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
		model.addAttribute("role", userdata.getUsertype());
		List<HouseDetailsModel> houses = userService.getAllHouseDetails();
	    model.addAttribute("houses", houses);
		return "user/sortbyprice";

	}

	@PostMapping("/applyFilters")
	public String applyFilters(Model model, HttpSession session, @RequestParam("city") String city,
			 @RequestParam("moveInDate") String moveInDate) {
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
        model.addAttribute("sessionMessages", messages);
        List<HouseDetailsModel> houses = userService.filterHouses(city,moveInDate);
        model.addAttribute("houses", houses);
        model.addAttribute("role", userdata.getUsertype());
		return "user/filter";
	}

	@PostMapping("/applyAdvanceFilters")
	public String applyAdvanceFilters(Model model, HttpSession session, @RequestParam("city") String city,
			 @RequestParam("moveInDate") String moveInDate, @RequestParam("parking") String parking,
			 @RequestParam("petFriendly") String petFriendly, @RequestParam("lawn") String lawn,  @RequestParam("houseType") String houseType) {
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
        model.addAttribute("sessionMessages", messages);
        List<HouseDetailsModel> houses = userService.advanceFilterHouses(city,moveInDate,parking,petFriendly,lawn,houseType);
        model.addAttribute("houses", houses);
        model.addAttribute("role", userdata.getUsertype());
		return "user/advancefilter";
	}

	@PostMapping("/sort")
	public String sort(Model model, HttpSession session, @RequestParam("price") String price){

		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		UserModel userdata = homeService.findUser(messages.get(0));
        model.addAttribute("sessionMessages", messages);
        List<HouseDetailsModel> houses = userService.sort(price);



        model.addAttribute("houses", houses);
        model.addAttribute("role", userdata.getUsertype());
		return "user/sortbyprice";
	}


}
