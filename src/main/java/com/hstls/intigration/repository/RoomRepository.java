package com.hstls.intigration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hstls.intigration.models.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{
	
	boolean existsByRoomNoAndParentHostelId(String roomNo, Long id);
}
