package com.hstls.intigration.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hostel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String ownerName;
	@ManyToOne
	@JoinColumn(name = "ownerEmail", referencedColumnName = "email")
	private User owner;
	private String contactNumber;
	private String address;
	private int rating;
	private String genderType;
	private LocalDate dateCreated;
	private Integer fourShareCount;
	private Integer threeShareCount;
	private Integer twoShareCount;
	private Integer oneShareCount;
	
}
