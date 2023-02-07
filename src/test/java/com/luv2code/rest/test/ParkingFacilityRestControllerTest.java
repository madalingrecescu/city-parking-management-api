package com.luv2code.rest.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.springframework.web.server.ResponseStatusException;

import com.luv2code.entity.City;
import com.luv2code.entity.ParkingFacility;
import com.luv2code.entity.ParkingFacilityType;
import com.luv2code.entity.Vehicle;
import com.luv2code.entity.VehicleType;
import com.luv2code.rest.ParkingFacilityRestController;
import com.luv2code.service.CityService;
import com.luv2code.service.ParkingFacilityService;

@ExtendWith(MockitoExtension.class)
class ParkingFacilityRestControllerTest {

	@Mock
	CityService cityService;

	@Mock
	ParkingFacilityService parkingFacilityService;

	@InjectMocks
	private ParkingFacilityRestController parkingFacilityRestController;

	private City city;
	private ParkingFacility parkingFacility;
	private Vehicle vehicle1;
	private Vehicle vehicle2;

	@BeforeEach
	public void setUp() {

		MockitoAnnotations.openMocks(this);

		city = new City("1", "Bucuresti", "B");

		parkingFacility = new ParkingFacility("1", "parcare", city, ParkingFacilityType.car_park, 100, 80);

		vehicle1 = new Vehicle("1", city, VehicleType.car, true, parkingFacility);
		vehicle2 = new Vehicle("2", city, VehicleType.car, false, null);
	}

		@Test
		public void createParkingFacilityTest() {
			
			when(parkingFacilityService.createParkingFacilityForSpecificCity(parkingFacility)).thenReturn(parkingFacility);
			
			parkingFacilityRestController.createParkingFacility(parkingFacility);
			
			verify(parkingFacilityService,times(1)).createParkingFacilityForSpecificCity(parkingFacility);
		}
		
		@Test
		public void GetParkingFacilityByIdTest_IdValid_ShouldWork() {
			
			when(parkingFacilityService.findParkingFacilityById(parkingFacility.getId())).thenReturn(parkingFacility);
			
			ParkingFacility result = parkingFacilityRestController.GetParkingFacilityById(parkingFacility.getId());
			
			assertEquals(parkingFacility, result);
			verify(parkingFacilityService, times(1)).findParkingFacilityById(parkingFacility.getId());
		}
		
		@Test
		public void GetParkingFacilityByIdTest_IdInvalid_ShouldThrowException() {
			
			when(parkingFacilityService.findParkingFacilityById("InvalidId")).thenReturn(null);
			
			assertThrows(ResponseStatusException.class, () -> parkingFacilityRestController.GetParkingFacilityById("InvalidId"));

		}

		@Test
		public void findAllParkingFacilitiesTest_CityFound_ShouldWork() {
			
			List<ParkingFacility> parkingFacilities = new ArrayList<>();
			parkingFacilities.add(parkingFacility);
			
			when(cityService.findCityById(city.getId())).thenReturn(city);
			
			when(parkingFacilityService.findParkingFacilitiesByCityId(city.getId())).thenReturn(parkingFacilities);
			
			assertEquals(parkingFacilities, parkingFacilityRestController.findAllParkingFacilities(city.getId()));
		}
		
		@Test
		public void findAllParkingFacilitiesTest_CityNotFound_ShouldThrowException() {
		
			when(cityService.findCityById("InvalidId")).thenReturn(null);
			
			assertThrows(ResponseStatusException.class, () -> parkingFacilityRestController.findAllParkingFacilities("InvalidId"));
		}
}
















