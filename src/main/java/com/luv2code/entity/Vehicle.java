package com.luv2code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Vehicle")
public class Vehicle {
	@Id
	@Column(name = "id")
	private String id;

	@ManyToOne
	@JoinColumn(name = "city_id")
	private City city;

	@Enumerated(EnumType.STRING)
	private VehicleType type;

	@Column(name = "is_parked")
	private boolean isParked;

	@ManyToOne
	@JoinColumn(name = "parking_facility_id", referencedColumnName = "id")
	private ParkingFacility parkingFacilityId;

	public Vehicle() {

	}

	

	public Vehicle(String id, City city, VehicleType type, boolean isParked, ParkingFacility parkingFacilityId) {
		this.id = id;
		this.city = city;
		this.type = type;
		this.isParked = isParked;
		this.parkingFacilityId = parkingFacilityId;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public VehicleType getType() {
		return type;
	}

	public void setType(VehicleType type) {
		this.type = type;
	}

	public boolean isParked() {
		return isParked;
	}

	public void setParked(boolean isParked) {
		this.isParked = isParked;
	}

	public ParkingFacility getParkingFacility() {
		return parkingFacilityId;
	}

	public void setParkingFacilityId(ParkingFacility parkingFacilityId) {
		this.parkingFacilityId = parkingFacilityId;
	}

}
