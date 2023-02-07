package com.luv2code.service.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.luv2code.dao.ParkingFacilityRepository;
import com.luv2code.dao.VehicleRepository;
import com.luv2code.entity.City;
import com.luv2code.entity.ParkingFacility;
import com.luv2code.entity.ParkingFacilityType;
import com.luv2code.entity.Vehicle;
import com.luv2code.entity.VehicleType;
import com.luv2code.service.VehicleServiceImpl;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

	@Mock
	private VehicleRepository vehicleRepository;
	
	@Mock
	private ParkingFacilityRepository parkingFacilityRepository;
	
	@InjectMocks
	private VehicleServiceImpl vehicleServiceImpl;
	
	private City city;
	private ParkingFacility parkingFacility;
	private Vehicle vehicle;
	
	@BeforeEach
	public void setUp(){
		
		MockitoAnnotations.openMocks(this);
		
		 city = new City("1","Bucuresti","B");
		
		 parkingFacility = new ParkingFacility("1", "parcare", city, ParkingFacilityType.car_park, 100, 80);
		
		 vehicle = new Vehicle("1", city, VehicleType.car, true, parkingFacility);
	}
	
	@Test
	public void createVehicleForSpecificCity_IdNotFound_ShouldCreateNewVehicle() {

		when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.empty());
		
		vehicleServiceImpl.createVehicleForSpecificCity(vehicle);
		
		verify(vehicleRepository,times(1)).save(vehicle);
	}
	
	@Test
	public void createVehicleForSpecificCity_IdFound_ShouldThrowException() {

		when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));
		
		assertThrows(ResponseStatusException.class, () -> vehicleServiceImpl.createVehicleForSpecificCity(vehicle));
		
		verify(vehicleRepository,times(0)).save(vehicle);
	}
	
	@Test
	public void getVehiclesByCityCodeTest(){
		
		List<Vehicle> vehicles= new ArrayList<>();
		vehicles.add(vehicle);
		
		when(vehicleRepository.findByCityCode(city.getCode())).thenReturn(vehicles);
		
		assertEquals(vehicles, vehicleServiceImpl.getVehiclesByCityCode(city.getCode()));
	}

	
	@Test
	public void parkVehicle_ValidVehicleAndParkingFacilityId_ShouldParkVehicle() {
		
		Vehicle unparkedVehicle = new Vehicle("2", city, VehicleType.car, false, null);
		
		when(vehicleRepository.findById(unparkedVehicle.getId())).thenReturn(Optional.of(unparkedVehicle));
		when(parkingFacilityRepository.findById(parkingFacility.getId())).thenReturn(Optional.of(parkingFacility));
		
		unparkedVehicle = vehicleServiceImpl.parkVehicle(unparkedVehicle.getId(), parkingFacility.getId());
		
		assertEquals(true, unparkedVehicle.isParked());
		assertEquals(parkingFacility,unparkedVehicle.getParkingFacility());
		assertEquals(79, parkingFacility.getAvailableCapacity());
		
		verify(vehicleRepository,times(1)).save(unparkedVehicle);
		verify(parkingFacilityRepository,times(1)).save(parkingFacility);
	}
	
	@Test
	public void unparkVehicle_ValidVehicleAndParkingFacilityId_ShouldParkVehicle() {
		
		
		when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));
		
		vehicle = vehicleServiceImpl.unparkVehicle(vehicle.getId());
		
		assertEquals(false, vehicle.isParked());
		assertEquals(null,vehicle.getParkingFacility());
		assertEquals(81, parkingFacility.getAvailableCapacity());
		
		verify(vehicleRepository,times(1)).save(vehicle);
		verify(parkingFacilityRepository,times(1)).save(parkingFacility);
	}
}



















