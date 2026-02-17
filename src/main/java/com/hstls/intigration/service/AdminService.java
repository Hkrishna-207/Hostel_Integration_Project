package com.hstls.intigration.service;

import java.util.List;

import com.hstls.intigration.models.Hostel;
import com.hstls.intigration.models.HostelEmployee;
import com.hstls.intigration.models.Room;
import com.hstls.intigration.models.User;

public interface AdminService {

	public List<Hostel> fetchHostelList(String email);
	
	public void addHostel(String email, Hostel hostel);
	
	public void addRoom(Long id,Room room);
	
	public User getCurrentAdmin(String email);
	
	public void saveEmployeeDetails(HostelEmployee emp,String roomNo, String ownerEmail);

	public int getHostelRequestsCount(String ownerEmail);
}
