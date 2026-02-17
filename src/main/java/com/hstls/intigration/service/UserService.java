package com.hstls.intigration.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.hstls.intigration.models.EmpRequests;
import com.hstls.intigration.models.Hostel;
import com.hstls.intigration.models.User;

public interface UserService extends UserDetailsService{

	public void addUser(User user);
	public void removeUser(Long id);
	public List<Hostel> getHostelList(String name, String location, Integer rating);
	public String saveEmpRequest(EmpRequests empRequest, Long hostel_id);

}
