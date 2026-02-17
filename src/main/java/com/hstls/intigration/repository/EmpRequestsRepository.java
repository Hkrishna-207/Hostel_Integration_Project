package com.hstls.intigration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hstls.intigration.models.EmpRequests;

public interface EmpRequestsRepository extends JpaRepository<EmpRequests, Long>{

	List<EmpRequests> findAllByAppliedHostelId(Long id);

}
