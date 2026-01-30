package com.hstls.intigration.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	@Column(nullable = false)
	private Integer rating=0;
	private String genderType;
	private LocalDate dateCreated;
	@OneToMany(mappedBy = "parentHostel", cascade = CascadeType.ALL)
	private List<Room> rooms;
	
}
