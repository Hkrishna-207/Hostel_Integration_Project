package com.hstls.intigration.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hstls.intigration.models.Hostel;
import com.hstls.intigration.models.User;
import com.hstls.intigration.repository.HostelRepository;
import com.hstls.intigration.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private HostelRepository hstlRepo;

	@GetMapping("/dashboard")
	public String showAdminDashboard() {
		return "adminDashboard";
	}
	
	@GetMapping("/addhostel")
	public String showAddHostelPage(Model model, Principal principal) {
		String email=principal.getName();
		User adminUser=userRepo.findByEmail(email);
		model.addAttribute("adminUser",adminUser);
		model.addAttribute("hostel",new Hostel());
		return "addHostel";
	}
	
	@PostMapping("/addhostel/save")
	public String saveTheAddedHostel(@ModelAttribute Hostel hostel,Principal principal) {
		String email=principal.getName();
		User currentUser=userRepo.findByEmail(email);
		//Set the user object as the owner (JPA handles the Id/email link)
		hostel.setOwner(currentUser);
		hstlRepo.save(hostel);
		
		return "redirect:/admin/dashboard";
		
	}
	
	@GetMapping("/removehostel")
	public String removeHostel() {
		return "redirect:/admin/dashboard";
	}
}
