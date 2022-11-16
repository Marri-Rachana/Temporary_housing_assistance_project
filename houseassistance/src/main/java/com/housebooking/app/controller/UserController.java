package com.housebooking.app.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import com.housebooking.app.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	private ReserveModel reserve;

	@Autowired
	private AppointmentModel appointment;

    @Autowired
	private ReviewOwnerModel review;

	@Autowired
	private FavouritesModel favourite;

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
//        String base64EncodedImage = Base64.getEncoder().encodeToString(houseOwnerService.getHouse().getHousePhoto());
//        model.addAttribute("image", base64EncodedImage);
//        System.out.println(base64EncodedImage);
        List<HouseModel> houses = userService.getAllHouses();
        model.addAttribute("houses", houses);
		return "user/welcomeuser";
	}

	@GetMapping("/reserve/{id}")
	public String reserve(@PathVariable(name="id") Long id, Model model, HttpSession session)
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
        model.addAttribute("reserve", reserve);
		return "user/reservehouse";
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
	public String bookAppointment(@RequestParam("houseid") Long houseid,@ModelAttribute("appointment") AppointmentModel appointment, Model model, HttpSession session) {

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
        appointment.setHouseDetails(house.getHouseDetails());

        appointment.setUserId(userdata.getId().toString());

        userService.saveAppointment(appointment);
        return "redirect:/user";

	}

	@PostMapping("/reserveHouse")
	public String reserveHouse( HttpSession session, Model model,
			@ModelAttribute("reserve") ReserveModel reserve,
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

        reserve.setHouseId(houseid);
        reserve.setUserId(userdata.getId().toString());

        try {
        	reserve.setDocument1(Base64.getEncoder().encodeToString(file1.getBytes()));
			reserve.setDocument2(Base64.getEncoder().encodeToString(file2.getBytes()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        int result = userService.saveReserveHouse(reserve);

        if(result == 1)
        {
        	return "redirect:/user";
        } else {
        	model.addAttribute("errormsg", "Invalid Coupon");
			return "home/error";
        }

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

		Set<AppointmentModel> bookings = userService.getAllAppointmentsByUserId(userdata.getId());

		model.addAttribute("bookings", bookings);
		return "user/previousbookings";
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

		userService.likeHouse(id);

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

		userService.disLikeHouse(id);

		return "redirect:/user";
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
		MessageModel msg = new MessageModel();
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
		List<HouseModel> houses = userService.searchHouses(searchKey);
		model.addAttribute("houses", houses);
		model.addAttribute("role", userdata.getUsertype());
		return "user/welcomeuser";
	}
}
