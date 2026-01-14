package com.hstls.intigration.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@GetMapping("/hostel_list")
	public String showHostelList(Model model,Principal principal) {
		String email=principal.getName();
		//User currentAdmin=userRepo.findByEmail(email);
		List<Hostel> adminHostels=hstlRepo.findAllByOwnerEmail(email);
		model.addAttribute("hostels",adminHostels);
		return "adminHostelList";
	}
	
	@GetMapping("/addhostel")
	public String showAddHostelPage(Model model, Principal principal) {
		String email=principal.getName();
		User adminUser=userRepo.findByEmail(email);
		model.addAttribute("adminUser",adminUser);
		Hostel hostel=new Hostel();
		hostel.setOwnerName(adminUser.getName());
		model.addAttribute("hostel",hostel);
		return "addHostel";
	}
	
	@PostMapping("/addhostel/save")
	public String saveTheAddedHostel(@ModelAttribute Hostel hostel,Principal principal,RedirectAttributes redirectAttributes) {
		String email=principal.getName();
		User currentUser=userRepo.findByEmail(email);
		//Set the user object as the owner (JPA handles the Id/email link)
		hostel.setOwner(currentUser);
		hostel.setOwnerName(currentUser.getName());
		hstlRepo.save(hostel);
		redirectAttributes.addFlashAttribute("message",hostel.getName()+ "Hostel added Successfully");
		
		return "redirect:/admin/dashboard";
		
	}
	
	@GetMapping("/removehostel/{id}")
	public String removeHostel(@PathVariable Long id) {
		hstlRepo.deleteById(id);
		return "redirect:/admin/dashboard";
	}
}
