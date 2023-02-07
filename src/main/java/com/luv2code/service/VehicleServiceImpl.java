package com.luv2code.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.luv2code.dao.ParkingFacilityRepository;
import com.luv2code.dao.VehicleRepository;
import com.luv2code.entity.ParkingFacility;
import com.luv2code.entity.ParkingFacilityType;
import com.luv2code.entity.Vehicle;
import com.luv2code.entity.VehicleType;

@Service
public class VehicleServiceImpl implements VehicleService{
	
	@Autowired
	private ParkingFacilityRepository parkingFacilityRepository;

	private VehicleRepository vehicleRepository;
	
	@Autowired
	public VehicleServiceImpl(VehicleRepository theVehicleRepository) {
		vehicleRepository = theVehicleRepository;
	}
	
	
	@Override
	public void createVehicleForSpecificCity(Vehicle theVehicle) {
		
		Optional<Vehicle> result = vehicleRepository.findById(theVehicle.getId());

		if (result.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vehicle id is already used - " + theVehicle.getId());
		}

		vehicleRepository.save(theVehicle);
	}


	@Override
	public List<Vehicle> getVehiclesByCityCode(String theCityCode) {
		List<Vehicle> vehicles = vehicleRepository.findByCityCode(theCityCode);
		
		return vehicles;
	}

	@Override
	public Vehicle parkVehicle(String vehicleId,String theParkgingFacilityId) {
		
		Optional<Vehicle> result1 = vehicleRepository.findById(vehicleId);
		
		Vehicle theVehicle = null;
		
		if(result1.isPresent()) {
			theVehicle = result1.get();
		}
		else {
			//need to change the exception
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Did not find the vehicle id - " + vehicleId);
		}
		
		Optional<ParkingFacility> result2 = parkingFacilityRepository.findById(theParkgingFacilityId);
		
		ParkingFacility theParkingFacility = null;
		
		if(result2.isPresent()) {
			theParkingFacility = result2.get();
		}
		else {
			//need to change the exception
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Did not find the parking repository id - " + theParkgingFacilityId);
		}
		

		if(theParkingFacility.getType() == ParkingFacilityType.car_park) {
			if(theVehicle.getType() == VehicleType.bike) {
				throw new IllegalStateException("Cannot park a bike in a car park ");
			}
			 
		}
		
		if(theParkingFacility.getType() == ParkingFacilityType.bike_rack) {
			if(theVehicle.getType() == VehicleType.car) {
				throw new IllegalStateException("Cannot park a car in a bike rack ");
			}
		}
			
		theVehicle.setParked(true);
		theVehicle.setParkingFacilityId(theParkingFacility);
		theParkingFacility.setAvailableCapacity(theParkingFacility.getAvailableCapacity() - 1);
		
		vehicleRepository.save(theVehicle);
		parkingFacilityRepository.save(theParkingFacility);
		
		return theVehicle;
	}

	@Override
	public Vehicle unparkVehicle(String vehicleId) {
		
		
		Optional<Vehicle> result1 = vehicleRepository.findById(vehicleId);
		
		Vehicle theVehicle = null;
		
		if(result1.isPresent()) {
			theVehicle = result1.get();
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Did not find the vehicle id - " + vehicleId);
		}
		
		if (!theVehicle.isParked()) {
	        throw new IllegalStateException("Vehicle with id " + vehicleId + " is not parked");
		}
		
		ParkingFacility theParkingFacility = theVehicle.getParkingFacility();
		
		theVehicle.setParked(false);
		theVehicle.setParkingFacilityId(null);
		theParkingFacility.setAvailableCapacity(theParkingFacility.getAvailableCapacity() + 1);
		
		vehicleRepository.save(theVehicle);
		parkingFacilityRepository.save(theParkingFacility);
		
		return theVehicle;
	}

}
