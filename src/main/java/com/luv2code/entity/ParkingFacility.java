package com.luv2code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ParkingFacility")
public class ParkingFacility {
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "city_id")
	private City city;

	@Enumerated(EnumType.STRING)
	@Column(name = "parking_facility_type")
	private ParkingFacilityType type;

	@Column(name = "capacity")
	private int capacity;

	@Column(name = "available_capacity")
	private int availableCapacity;
	
	public ParkingFacility() {

	}

	public ParkingFacility(String id, String name, City city, ParkingFacilityType type, int capacity,
			int availableCapacity) {
		this.id = id;
		this.name = name;
		this.city = city;
		this.type = type;
		this.capacity = capacity;
		this.availableCapacity = availableCapacity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public ParkingFacilityType getType() {
		return type;
	}

	public void setType(ParkingFacilityType type) {
		this.type = type;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getAvailableCapacity() {
		return availableCapacity;
	}

	public void setAvailableCapacity(int availableCapacity) {
		this.availableCapacity = availableCapacity;
	}


}
