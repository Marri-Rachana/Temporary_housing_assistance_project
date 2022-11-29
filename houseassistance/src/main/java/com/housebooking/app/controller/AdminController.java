package com.housebooking.app.controller;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.housebooking.app.model.Announcement;
import com.housebooking.app.model.Coupon;
import com.housebooking.app.model.FAQModel;
import com.housebooking.app.model.ReportModel;
import com.housebooking.app.model.TicketModel;
import com.housebooking.app.model.UserModel;
import com.housebooking.app.service.AdminService;
import com.housebooking.app.service.HomeService;
import com.housebooking.app.service.HouseOwnerService;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private HomeService homeService;

	@Autowired
	private HouseOwnerService houseOwnerService;

	@Autowired
	private Announcement announcement;

	@Autowired
	private FAQModel faq;

	@Autowired
	private Coupon coupon;

	@GetMapping("/admin")
	public String getAdminWelcomePage(@ModelAttribute("user") UserModel user, Model model, HttpSession session)
	{
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
        model.addAttribute("role", "admin");
		return "admin/welcomeadmin";
	}


	@GetMapping("/faqs")
	public String getFaqPage(Model model) {
		return "admin/faqs";

	}



	@GetMapping("/spamStudents")
	public String getSpamStudents(Model model) {

		List<ReportModel> studentReports = adminService.findAllStudentReports();
		model.addAttribute("reports", studentReports);
		return "admin/spamstudents";

	}

	@GetMapping("/viewTickets")
	public String viewTickets(Model model) {

		List<TicketModel> tickets = adminService.findAllTickets();
		model.addAttribute("tickets",tickets);
		return "admin/viewtickets";

	}

	@GetMapping("/removeStudent/{id}")
	public String removeSpamStudents(Model model, @PathVariable("id") Long id) {
		System.out.println("id==== "+id);
		adminService.removeStudent(id);
		return "redirect:/admin";

	}

	@GetMapping("/removeTicket/{id}")
	public String removeTicket(Model model, @PathVariable("id") Long id) {
		System.out.println("id==== "+id);
		adminService.removeTicket(id);
		return "redirect:/admin";

	}



	@GetMapping("/spamHouses")
	public String getSpamHouses(Model model) {

		List<ReportModel> houseReports = adminService.findAllHousesReports();
		model.addAttribute("reports", houseReports);
		return "admin/spamowners";

	}

	@GetMapping("/removeHouse/{id}")
	public String removeSpamHouses(Model model, @PathVariable("id") Long id) {

		adminService.removeHouse(id);
		return "redirect:/admin";

	}

	@GetMapping("/announcement")
	public String getAnnouncementPage(Model model) {

		model.addAttribute("announcement", announcement);
		return "admin/addannouncement";

	}


	@PostMapping("/postAnnouncement")
	public String postAnnouncement(@ModelAttribute("announcement") Announcement announcement) {

		adminService.addAnnouncement(announcement);

		return "redirect:/admin";

	}

	@GetMapping("/createFaq")
	public String createFaqPage(Model model) {

		model.addAttribute("faq", faq);
		return "admin/addfaq";

	}

	@GetMapping("/viewFaqs")
	public String viewFaqsPage(Model model) {

		List<FAQModel> faqs = adminService.findAllFAQs();
		model.addAttribute("faqs", faqs);
		return "admin/viewfaqs";

	}


	@PostMapping("/postFaq")
	public String postFaq(@ModelAttribute("faq") FAQModel faq) {

		adminService.addFaq(faq);

		return "redirect:/admin";

	}

	@GetMapping("/editFaq/{id}")
	public String editFaqPage(Model model, @PathVariable("id") Long id) {
		FAQModel faq = adminService.findFAQById(id);
		model.addAttribute("faq",faq );
		return "admin/editfaq";

	}

	@PostMapping("/updateFaq")
	public String updateFaq(@ModelAttribute("faq") FAQModel faq) {

		adminService.addFaq(faq);

		return "redirect:/admin";

	}

	@GetMapping("/coupon")
	public String viewCouponPage(Model model) {

		model.addAttribute("coupon",coupon);
		return "admin/coupon";

	}

	@PostMapping("/postCoupon")
	public String updateFaq(@ModelAttribute("coupon") Coupon coupon) {

		adminService.addCoupon(coupon);

		return "redirect:/admin";

	}

}
