package com.hstls.intigration.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpRequests {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String empName;
	private String empAge;
	private String contactNo;
	private String workingCompany;
	private Integer roomPref;
	@ManyToOne
	@JoinColumn(name="appliedHostel_id", referencedColumnName = "id")
	private Hostel appliedHostel;
}
