package com.hstls.intigration.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hstls.intigration.exception.RoomAlreadyExistsException;
import com.hstls.intigration.models.EmpRequests;
import com.hstls.intigration.models.Hostel;
import com.hstls.intigration.models.HostelEmployee;
import com.hstls.intigration.models.Room;
import com.hstls.intigration.models.User;
import com.hstls.intigration.repository.HostelRepository;
import com.hstls.intigration.repository.RoomRepository;
import com.hstls.intigration.repository.UserRepository;
import com.hstls.intigration.service.AdminService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AdminService adminService;

	@Autowired
	HostelRepository hstlRepo;

	@Autowired
	private RoomRepository roomRepo;
	
	@GetMapping("/showrequests")
	public String showRequestsPage(Principal principal, Model model) {
		List<EmpRequests> empRequests=adminService.getHostelRequests(principal.getName());
		model.addAttribute("empRequests",empRequests);
		return "showEmpRequests";
	}

	@GetMapping("/dashboard")
	public String showAdminDashboard(Principal principal, Model model) {
		String ownerEmail=principal.getName();
		int requestCount=adminService.getHostelRequests(ownerEmail).size();
		model.addAttribute("requestCount", requestCount);
		return "adminDashboard";
	}

	@GetMapping("/hostel_list")
	public String showHostelList(Model model, Principal principal) {
		String email = principal.getName();
		List<Hostel> adminHostels = adminService.fetchHostelList(email);
		model.addAttribute("hostels", adminHostels);
		return "adminHostelList";
	}

	@GetMapping("/addhostel")
	public String showAddHostelPage(Model model, Principal principal) {
		String email = principal.getName();
		User adminUser = adminService.getCurrentAdmin(email);
		model.addAttribute("adminUser", adminUser);
		Hostel hostel = new Hostel();
		hostel.setOwnerName(adminUser.getName());
		model.addAttribute("hostel", hostel);
		return "addHostel";
	}

	@PostMapping("/addhostel/save")
	public String saveTheAddedHostel(@ModelAttribute Hostel hostel, Principal principal,
			RedirectAttributes redirectAttributes) {
		String email = principal.getName();
		adminService.addHostel(email, hostel);
		redirectAttributes.addFlashAttribute("message", hostel.getName() + "Hostel added Successfully");

		return "redirect:/admin/dashboard";

	}

	@GetMapping("/removehostel/{id}")
	public String removeHostel(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		hstlRepo.deleteById(id);
		redirectAttributes.addFlashAttribute("message", "Hostel  Removed Successfully");
		return "redirect:/admin/dashboard";
	}

	@GetMapping("/addroom/{id}")
	public String showAddRoomPage(@PathVariable Long id, Model model) {
		Hostel hostel = hstlRepo.findById(id).orElseThrow();
		model.addAttribute("hostel", hostel);
		model.addAttribute("room", new Room());
		return "addRoom";
	}

	@PostMapping("/addroom/save")
	public String SaveAddRoom(@Valid @ModelAttribute Room room, BindingResult result, @PathParam("hostelId") Long hostelId,
			Model model, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			model.addAttribute("hostel", hstlRepo.findById(hostelId).get());
			return "addRoom";
		}
		try {
		adminService.addRoom(hostelId, room);
		}catch (RoomAlreadyExistsException e) {
			result.rejectValue("roomNo", "error.room", e.getMessage());
			model.addAttribute("hostel", hstlRepo.findById(hostelId).get());
			return "addRoom";
		}
		redirectAttributes.addFlashAttribute("message", "Room Added Successfully");
		return "redirect:/admin/hostel_list";
	}
	
	@GetMapping("/addEmployee")
	public String showAddEmployeeForm(Model model) {
		model.addAttribute("emp", new HostelEmployee());
		return "addEmployeeToRoom";
	}
	
	@PostMapping("/addEmployeeToRoom")
	public String saveEmployeeDetails(@ModelAttribute HostelEmployee emp, @PathParam("roomNo") String roomNo, @PathParam("hostelName") String hostelName){
	
		adminService.saveEmployeeDetails(emp, roomNo, hostelName);
		return "redirect:/admin/dashboard";
	}
}
