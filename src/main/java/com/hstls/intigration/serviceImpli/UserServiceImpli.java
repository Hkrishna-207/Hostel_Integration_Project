package com.hstls.intigration.serviceImpli;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hstls.intigration.models.EmpRequests;
import com.hstls.intigration.models.Hostel;
import com.hstls.intigration.models.User;
import com.hstls.intigration.repository.EmpRequestsRepository;
import com.hstls.intigration.repository.HostelRepository;
import com.hstls.intigration.repository.UserRepository;
import com.hstls.intigration.service.UserService;


@Service
public class UserServiceImpli implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private HostelRepository hostelRepo;
	
	@Autowired
	private EmpRequestsRepository empRequestRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//fetch the user from database by using repository
		User user=userRepo.findByEmail(email);
		
		if(user==null) {
			throw new UsernameNotFoundException("user not found with email : "+email);
		}
		String userRole = user.getRole();
	    if (userRole == null) {
	        userRole = "ROLE_USER"; // Default to USER if null in DB
	    }
		//convert the  database user into spring's UserDetails Object
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getEmail())
				.password(user.getPassword())
				.roles(userRole.replace("ROLE_", ""))// spring adds role automatically
				.build();
	}

	@Override
	public void addUser(User user) {
		String encodedPassword=passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepo.save(user);
		
	}

	@Override
	public void removeUser(Long id) {
		userRepo.deleteById(id);
		
	}

	@Override
	public List<Hostel> getHostelList(String name, String location, Integer rating) {
		
		return hostelRepo.filterHostels(name, location, rating);
	}

	@Override
	public void saveEmpRequest(EmpRequests empRequest, Long hostel_id) {
		Hostel appliedHostel=hostelRepo.findById(hostel_id).orElseThrow();
		empRequest.setAppliedHostel(appliedHostel);
		empRequestRepo.save(empRequest);
		
	}
	
	
	
	

	

	

}
