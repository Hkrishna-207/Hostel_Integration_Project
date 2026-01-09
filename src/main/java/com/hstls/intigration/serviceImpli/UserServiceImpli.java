package com.hstls.intigration.serviceImpli;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hstls.intigration.models.User;
import com.hstls.intigration.repository.UserRepository;
import com.hstls.intigration.service.UserService;


@Service
public class UserServiceImpli implements UserService {
	
	@Autowired
	private UserRepository userRepo;

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

	

	

}
