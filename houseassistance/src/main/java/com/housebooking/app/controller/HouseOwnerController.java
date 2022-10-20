package com.housebooking.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.housebooking.app.model.HouseModel;
import com.housebooking.app.model.ReportModel;
import com.housebooking.app.model.ReviewModel;
import com.housebooking.app.model.UserModel;
import com.housebooking.app.service.HomeService;
import com.housebooking.app.service.HouseOwnerService;
import com.housebooking.app.utils.FileUploadUtil;

@Controller
public class HouseOwnerController {
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private HouseOwnerService houseOwnerService;
	
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
//        String base64EncodedImage = Base64.getEncoder().encodeToString(houseOwnerService.getHouse().getHousePhoto());
//        model.addAttribute("image", base64EncodedImage);
//        System.out.println(base64EncodedImage);
		return "houseowner/welcomehouseowner";
	}
	
	@GetMapping("/createHouse")
	public String createHouse(Model model, HttpSession session) {
		
		HouseModel house = new HouseModel();
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
	public String saveHouseBooking(@ModelAttribute("house") HouseModel house, @RequestParam("image") MultipartFile multipartFile)
	{
		System.out.println("house created");
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		try {
			house.setHousePhoto(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		house.setIsBooked("0");
		house.setIsHide("0");
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
		ReportModel report = new ReportModel();
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
		ReviewModel review = new ReviewModel();
		model.addAttribute("review", review);
		model.addAttribute("role", userdata.getUsertype());

		return "houseowner/reviewapp";
	}
	
	@PostMapping("/reviewApp")
	public String reviewApp(@ModelAttribute("review") ReviewModel review)
	{
		System.out.println("reviewed App");
		

		houseOwnerService.saveReview(review);
		
		return "redirect:/houseowner";
	}
}
