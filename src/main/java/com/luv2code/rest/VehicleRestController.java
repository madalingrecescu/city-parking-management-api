package com.luv2code.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.luv2code.entity.City;
import com.luv2code.entity.ParkingFacility;
import com.luv2code.entity.Vehicle;
import com.luv2code.service.CityService;
import com.luv2code.service.VehicleService;

@RestController
@RequestMapping("/api")
public class VehicleRestController {
	
	@Autowired
	private CityService cityService;

	private VehicleService vehicleService;
	
	@Autowired
	public VehicleRestController(VehicleService theVehicleService) {
		
		vehicleService = theVehicleService;
	}
	
	@PostMapping("/vehicle")
	public Vehicle createVehicleForAGivenCity(@RequestBody Vehicle theVehicle) {
		
		vehicleService.createVehicleForSpecificCity(theVehicle);
		
		return theVehicle;
	}
	
	@GetMapping("/vehicles/code/{cityCode}")
	public List<Vehicle> getVehiclesByCityCode(@PathVariable String cityCode){
		
		City theCity = cityService.findCityByCode(cityCode);
		if (theCity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found with id: " + cityCode);
		}
		
		return vehicleService.getVehiclesByCityCode(cityCode);
	}
	
	@PutMapping("/vehicle/park/vehicle/{vehicleId}/parkingFacility/{parkingFacilityId}")
	public Vehicle parkVehicle(@PathVariable("vehicleId") String vehicleId,
								@PathVariable("parkingFacilityId") String parkingFacilityId) {
		
		return vehicleService.parkVehicle(vehicleId, parkingFacilityId);
	}
	
	@PutMapping("/vehicle/unPark/{vehicleId}")
	public Vehicle unParkVehicle(@PathVariable("vehicleId") String vehicleId) {
		
		return vehicleService.unparkVehicle(vehicleId);
	}
}

















