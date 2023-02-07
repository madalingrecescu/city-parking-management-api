package com.luv2code.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.luv2code.dao.CityRepository;
import com.luv2code.dao.ParkingFacilityRepository;
import com.luv2code.entity.City;
import com.luv2code.entity.ParkingFacility;

@Service
public class ParkingFacilityServiceImpl implements ParkingFacilityService {
	
	private ParkingFacilityRepository parkingFacilityRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	public ParkingFacilityServiceImpl(ParkingFacilityRepository theParkingFacility) {
		
		parkingFacilityRepository = theParkingFacility;
	}

	@Override
	public ParkingFacility createParkingFacilityForSpecificCity(ParkingFacility theParkingFacility) {

		Optional<ParkingFacility> result = parkingFacilityRepository.findById(theParkingFacility.getId());

		if (result.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Parking facility id is already used - " + theParkingFacility.getId());
		}

		parkingFacilityRepository.save(theParkingFacility);
		return theParkingFacility;
	}

	@Override
	public ParkingFacility findParkingFacilityById(String theId) {

			Optional<ParkingFacility> result = parkingFacilityRepository.findById(theId);
			
			ParkingFacility theParkingFacility = null;
			
			if(result.isPresent()) {
				theParkingFacility = result.get();
			}
			else {
				//need to change the exception
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Did not find the parking repository id - " + theId);
			}
			return theParkingFacility;
		
	}

	@Override
	public List<ParkingFacility> findParkingFacilitiesByCityId(String theCityId) {
		
		List<ParkingFacility> parkingFacilities = parkingFacilityRepository.findByCityId(theCityId);
		
		return parkingFacilities;
	}

}
