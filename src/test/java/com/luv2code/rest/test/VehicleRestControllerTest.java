package com.luv2code.rest.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.luv2code.entity.City;
import com.luv2code.entity.ParkingFacility;
import com.luv2code.entity.ParkingFacilityType;
import com.luv2code.entity.Vehicle;
import com.luv2code.entity.VehicleType;
import com.luv2code.rest.VehicleRestController;
import com.luv2code.service.CityService;
import com.luv2code.service.ParkingFacilityService;
import com.luv2code.service.VehicleService;

@ExtendWith(MockitoExtension.class)
public class VehicleRestControllerTest {

	@Mock
	CityService cityService;
	
	@Mock
	VehicleService vehicleService;
	
	@InjectMocks
	private VehicleRestController vehicleRestController;
	
	private City city;
	private ParkingFacility parkingFacility;
	private Vehicle vehicle1;
	private Vehicle vehicle2;
	
	@BeforeEach
	public void setUp(){
		
		MockitoAnnotations.openMocks(this);
		
		 city = new City("1","Bucuresti","B");
		
		 parkingFacility = new ParkingFacility("1", "parcare", city, ParkingFacilityType.car_park, 100, 80);
		
		 vehicle1 = new Vehicle("1", city, VehicleType.car, true, parkingFacility);
		 vehicle2 = new Vehicle("2", city, VehicleType.car, false, null);
	}
	
	
	@Test
	public void testCreateVehicleForAGivenCity() {
		
		Vehicle resultVehicle = vehicleRestController.createVehicleForAGivenCity(vehicle1);
		
		assertEquals(vehicle1, resultVehicle);
	}
	
	@Test
	public void testGetVehiclesByCityCode_Success() {
		
		when(cityService.findCityByCode("B")).thenReturn(city);
		
		List<Vehicle> vehicles = new ArrayList<>();
		vehicles.add(vehicle1);
		vehicles.add(vehicle2);
		
		when(vehicleService.getVehiclesByCityCode("B")).thenReturn(vehicles);
		
		List<Vehicle> result = vehicleRestController.getVehiclesByCityCode("B");
		
		assertEquals(vehicles, result);
	}
	
	@Test
	public void testParkVehicle() {
		
		when(vehicleService.parkVehicle(vehicle2.getId(),parkingFacility.getId())).thenReturn(vehicle2);
		
		vehicleRestController.parkVehicle(vehicle2.getId(), parkingFacility.getId());
		
		verify(vehicleService, times(1)).parkVehicle(vehicle2.getId(), parkingFacility.getId());
	}
	
	@Test
	public void testUnParkVehicle() {
		
		when(vehicleService.unparkVehicle(vehicle1.getId())).thenReturn(vehicle1);
		
		vehicleRestController.unParkVehicle(vehicle1.getId());
		
		verify(vehicleService, times(1)).unparkVehicle(vehicle1.getId());
	}
}


















