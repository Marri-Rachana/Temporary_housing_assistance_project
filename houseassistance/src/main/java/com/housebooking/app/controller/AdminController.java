package com.housebooking.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.housebooking.app.model.Announcement;
import com.housebooking.app.model.Notification;
import com.housebooking.app.model.UserModel;
import com.housebooking.app.service.AdminService;
import com.housebooking.app.service.HomeService;

@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private HomeService homeService;
	
	@GetMapping("/admin")
	public String getAdminWelcomePage(@ModelAttribute("user") UserModel user, Model model, HttpSession session)
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
		return "admin/welcomeadmin";
	}
	
	@GetMapping("/notification")
	public String getNotificationPage(Model model) {
		
		Notification notification = new Notification();
		
		model.addAttribute("notification", notification);
		return "admin/addnotification";
		
	}
	
	@PostMapping("/postNotification")
	public String postNotification(@ModelAttribute("notification") Notification notification) {
		
		adminService.addNotification(notification);
		
		return "redirect:/admin";
		
	}
	
	@GetMapping("/announcement")
	public String getAnnouncementPage(Model model) {
		
		Announcement announcement = new Announcement();
		
		model.addAttribute("announcement", announcement);
		return "admin/addannouncement";
		
	}
	
	@PostMapping("/postAnnouncement")
	public String postNotification(@ModelAttribute("announcement") Announcement announcement) {
		
		adminService.addAnnouncement(announcement);
		
		return "redirect:/admin";
		
	}
	
}
