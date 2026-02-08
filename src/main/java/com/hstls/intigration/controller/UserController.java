package com.hstls.intigration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hstls.intigration.models.Hostel;
import com.hstls.intigration.models.User;
import com.hstls.intigration.repository.HostelRepository;
import com.hstls.intigration.repository.UserRepository;
import com.hstls.intigration.service.UserService;


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
	
	@GetMapping("/user/applyform")
	public String showApplyForm() {
		return "applyForm";
	}

}
