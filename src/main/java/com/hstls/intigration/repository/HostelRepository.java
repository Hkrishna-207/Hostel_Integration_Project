package com.hstls.intigration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hstls.intigration.models.Hostel;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, Long> {
	
}
