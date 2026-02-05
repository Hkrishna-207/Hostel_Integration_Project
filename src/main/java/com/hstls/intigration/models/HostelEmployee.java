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
public class HostelEmployee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private  String  empName;
	private String empAge;
	private String workingCompany;
	private String empAddress;
	@ManyToOne
	@JoinColumn(name = "room_no", referencedColumnName = "roomNo")
	private Room roomOwned;
	@ManyToOne
	@JoinColumn( name= "hostel_id", referencedColumnName= "id")
	private Hostel hostelOwned;
}
