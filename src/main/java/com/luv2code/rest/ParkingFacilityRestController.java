package com.luv2code.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.luv2code.entity.City;
import com.luv2code.entity.ParkingFacility;
import com.luv2code.service.CityService;
import com.luv2code.service.ParkingFacilityService;

@RestController
@RequestMapping("/api")
public class ParkingFacilityRestController {

	@Autowired
	private CityService cityService;
	
	private ParkingFacilityService parkingFacilityService;
	
	@Autowired
	public ParkingFacilityRestController(ParkingFacilityService theParkingFacilityService) {
		
		parkingFacilityService = theParkingFacilityService;
	}
	
	//expose "/parking" endpoint and create a new parking facility
	
	@PostMapping("/parking")
	public ParkingFacility createParkingFacility(@RequestBody ParkingFacility theParkingFacility) {
		
		parkingFacilityService.createParkingFacilityForSpecificCity(theParkingFacility);
		
		return theParkingFacility;
	}
	
	//expose "/parking/{parkingFacilityId}" and return a single parking facility by id
	@GetMapping("/parking/{parkingFacilityId}")
	public ParkingFacility GetParkingFacilityById(@PathVariable String parkingFacilityId) {
		
		ParkingFacility theParkingFacility = parkingFacilityService.findParkingFacilityById(parkingFacilityId);
		
		if(theParkingFacility == null) {
			
			// need new Exception handling
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Parking Facility id not found - " + parkingFacilityId);
		}
		
		return theParkingFacility;
	}
	
	//expose "/parking/{cityId}" and return all the parking facilities for a given city
	
	@GetMapping("/parking/city/{cityId}")
	public List<ParkingFacility> findAllParkingFacilities(@PathVariable String cityId){
		
		City theCity = cityService.findCityById(cityId);
		if (theCity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found with id: " + cityId);
		}
		
		return parkingFacilityService.findParkingFacilitiesByCityId(cityId);
		
	}
	
}








