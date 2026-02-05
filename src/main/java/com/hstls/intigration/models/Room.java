package com.hstls.intigration.models;

import java.util.List;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
		@UniqueConstraint( columnNames = {"roomNo", "hostel_id"})
})
public class Room {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String roomNo;
	private Integer bedCount;
	private Double pricePerBed;
	@ManyToOne
	@JoinColumn(name="hostel_id", referencedColumnName = "id", nullable = false)
	private Hostel parentHostel;
	@OneToMany(mappedBy = "roomOwned", cascade = CascadeType.ALL)
	private List<HostelEmployee> roomMembers;
	
}
