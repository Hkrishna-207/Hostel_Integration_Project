package com.hstls.intigration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hstls.intigration.models.Hostel;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, Long> {
	
	@Query("Select h from Hostel h where "+
	"(:name is NULL OR h.name LIKE %:name% ) AND "+
	"(:location is NULL OR h.address LIKE %:location%) AND "+
	"(:rating is NULL OR h.rating>=:rating)")
	List<Hostel> filterHostels(@Param("name") String name,@Param("location") String location,@Param("rating") int rating);
}
