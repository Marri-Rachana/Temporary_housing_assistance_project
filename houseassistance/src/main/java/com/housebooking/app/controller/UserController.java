package com.housebooking.app.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.housebooking.app.model.TicketModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.housebooking.app.model.UserModel;
import com.housebooking.app.service.HomeService;
import com.housebooking.app.service.HouseOwnerService;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {
	@Autowired
	private HomeService homeService;

	@Autowired
	private HouseOwnerService houseOwnerService;

	@Autowired
	private TicketModel ticket;
	
	@GetMapping("/user")
	public String getUserWelcomePage(@ModelAttribute("user") UserModel user, Model model, HttpSession session)
	{
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

        if (messages == null) {
            messages = new ArrayList<>();
        }
        model.addAttribute("sessionMessages", messages);
        UserModel userdata = homeService.findUser(messages.get(0));
        model.addAttribute("role", userdata.getUsertype());
//        String base64EncodedImage = Base64.getEncoder().encodeToString(houseOwnerService.getHouse().getHousePhoto());
//        model.addAttribute("image", base64EncodedImage);
//        System.out.println(base64EncodedImage);
		return "user/welcomeuser";
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
}
