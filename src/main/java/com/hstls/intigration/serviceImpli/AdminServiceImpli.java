package com.hstls.intigration.serviceImpli;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hstls.intigration.exception.RoomAlreadyExistsException;
import com.hstls.intigration.models.Hostel;
import com.hstls.intigration.models.Room;
import com.hstls.intigration.models.User;
import com.hstls.intigration.repository.HostelRepository;
import com.hstls.intigration.repository.RoomRepository;
import com.hstls.intigration.repository.UserRepository;
import com.hstls.intigration.service.AdminService;

@Service
public class AdminServiceImpli implements AdminService{
	
	@Autowired
    private RoomRepository roomRepo;
	
	@Autowired
	private HostelRepository hostelRepo;
	
	@Autowired
	private UserRepository userRepo;

    

	@Override
	public List<Hostel> fetchHostelList(String email) {
		//return hostelRepo.findAllByOwnerEmail(email);
		return userRepo.findByEmail(email).getHostels();
	}

	@Override
	public void addHostel(String email, Hostel hostel) {
		User currentUser=userRepo.findByEmail(email);
		hostel.setOwner(currentUser);
		hostelRepo.save(hostel);
	}

	@Override
	public User getCurrentAdmin(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public void addRoom(Long id, Room room) {
		boolean exists=roomRepo.existsByRoomNoAndParentHostelId(room.getRoomNo(), id);
		if(exists) {
			throw new RoomAlreadyExistsException("The Room Number is already exists....!!!");
		}
		Hostel parent=hostelRepo.findById(id).orElseThrow();
		room.setParentHostel(parent);
		roomRepo.save(room);
	}

}
