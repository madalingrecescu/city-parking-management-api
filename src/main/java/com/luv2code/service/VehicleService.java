package com.luv2code.service;

import java.util.List;

import com.luv2code.entity.Vehicle;

public interface VehicleService {

	public void createVehicleForSpecificCity(Vehicle vehicle);

	public List<Vehicle> getVehiclesByCityCode(String theCityCode);
	
	public Vehicle parkVehicle(String vehicleId,String theParkgingFacilityId);
	
	public Vehicle unparkVehicle(String vehicleId);
}
