package com.luv2code.service;

import java.util.List;

import com.luv2code.entity.ParkingFacility;

public interface ParkingFacilityService {

	public ParkingFacility createParkingFacilityForSpecificCity(ParkingFacility theParkingFacility);
	
	public ParkingFacility findParkingFacilityById(String theId);
	
	public List<ParkingFacility> findParkingFacilitiesByCityId(String theCityId);
}
