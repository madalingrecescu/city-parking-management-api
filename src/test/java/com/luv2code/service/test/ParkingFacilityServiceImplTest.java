package com.luv2code.service.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import com.luv2code.dao.ParkingFacilityRepository;
import com.luv2code.entity.City;
import com.luv2code.entity.ParkingFacility;
import com.luv2code.entity.ParkingFacilityType;
import com.luv2code.service.ParkingFacilityServiceImpl;

class ParkingFacilityServiceImplTest {
	
	@Mock
	ParkingFacilityRepository parkingFacilityRepository;
	
	@InjectMocks
	ParkingFacilityServiceImpl parkingFacilityServiceImpl;
	
	private City city;
	private ParkingFacility parkingFacility;
	
	@BeforeEach
	public void setUp() {
		
		MockitoAnnotations.openMocks(this);
		
		 city = new City("1","Bucuresti","B");
		
		 parkingFacility = new ParkingFacility("1", "parcare", city, ParkingFacilityType.car_park, 100, 80);
	}

	@Test
	public void createParkingFacilityForSpecificCity_IdNotFound_ShouldSaveParkingFacility() {
		
		when(parkingFacilityRepository.findById(parkingFacility.getId())).thenReturn(Optional.empty());
		
		parkingFacilityServiceImpl.createParkingFacilityForSpecificCity(parkingFacility);
		
		verify(parkingFacilityRepository, times(1)).save(parkingFacility);
	}
	
	@Test
	public void createParkingFacilityForSpecificCity_IdFound_ShouldThrowException() {
		
		when(parkingFacilityRepository.findById(parkingFacility.getId())).thenReturn(Optional.of(parkingFacility));
		
		assertThrows(ResponseStatusException.class, () -> parkingFacilityServiceImpl.createParkingFacilityForSpecificCity(parkingFacility));
		
		verify(parkingFacilityRepository,times(0)).save(parkingFacility);
	}
	
	@Test
	public void findParkingFacilityById_IdFound_shouldReturnParkingFacility() {
		
		when(parkingFacilityRepository.findById(parkingFacility.getId())).thenReturn(Optional.of(parkingFacility));
		
		ParkingFacility result = parkingFacilityServiceImpl.findParkingFacilityById(parkingFacility.getId());
		
		assertEquals(parkingFacility, result);
	}

	@Test
	public void findParkingFacilityById_IdNotFound_shouldThrowException() {
		
		when(parkingFacilityRepository.findById(parkingFacility.getId())).thenReturn(Optional.empty());
		
		assertThrows(ResponseStatusException.class, () -> parkingFacilityServiceImpl.findParkingFacilityById(parkingFacility.getId()));
	}
	
	@Test
	public void findParkingFacilitiesByCityIdTest() {
		
		List<ParkingFacility> parkingFacilities = new ArrayList<>();
		parkingFacilities.add(parkingFacility);
		
		when(parkingFacilityRepository.findByCityId(city.getId())).thenReturn(parkingFacilities);
		
		assertEquals(parkingFacilities, parkingFacilityServiceImpl.findParkingFacilitiesByCityId(city.getId()));
	}
}




















