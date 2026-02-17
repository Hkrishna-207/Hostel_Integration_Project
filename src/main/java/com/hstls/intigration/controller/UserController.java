package com.hstls.intigration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hstls.intigration.models.EmpRequests;
import com.hstls.intigration.models.Hostel;
import com.hstls.intigration.models.User;
import com.hstls.intigration.repository.HostelRepository;
import com.hstls.intigration.repository.UserRepository;
import com.hstls.intigration.service.UserService;

import jakarta.websocket.server.PathParam;


@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String home() {
		return "homepage";
	}

	@GetMapping("/log")
	public String login() {
		return "login";
	}
	
	@GetMapping("/user/dashboard")
	public String showUserDashboard() {
		return "userDashboard";
	}

	@GetMapping("/register")
	public String register() {
		return "registration";
	}
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute User user) {
		userService.addUser(user);

		return "redirect:/log?success";
	}
	
	@GetMapping("/user/showhostels")
	public String showListOfHostels(@RequestParam(required = false) String name,@RequestParam(required = false) String location,@RequestParam(required = false) String rating,Model model) {
		
		List<Hostel> resultList=userService.getHostelList(name, location, rating==null?0:Integer.parseInt(rating));//Integer.parseInt(rating)

		model.addAttribute("hostels",resultList);
		return "showHostels";
	}
	
	@GetMapping("/user/applyform/{id}")
	public String showApplyForm(@PathVariable Long id, Model model) {
		model.addAttribute("empRequest", new EmpRequests());
		model.addAttribute("hostel_id", id);
		return "applyForm";
	}
	
	@PostMapping("/user/saveEmpRequest")
	public String saveEmpRequests(@ModelAttribute EmpRequests empRequest, @PathParam("hostel_id") Long hostel_id, RedirectAttributes redirectAttributes) {
		String message=userService.saveEmpRequest(empRequest, hostel_id);
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/user/dashboard";
	}

}
