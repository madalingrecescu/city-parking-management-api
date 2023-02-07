package com.luv2code.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "City")
public class City {
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "code")
	private String code;
	
	@JsonIgnore
	@OneToMany(mappedBy = "city")
	private List<ParkingFacility> parkingFacilities;

	public City() {

	}


	public City(String id, String name, String code) {
		this.id = id;
		this.name = name;
		this.code = code;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<ParkingFacility> getParkingFacilities() {
		return parkingFacilities;
	}

	public void setParkingFacilities(List<ParkingFacility> parkingFacilities) {
		this.parkingFacilities = parkingFacilities;
	}

	// Getters and setters

}
