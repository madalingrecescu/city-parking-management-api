package com.luv2code.rest.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
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
import com.luv2code.rest.CityRestController;
import com.luv2code.service.CityService;

@ExtendWith(MockitoExtension.class)
class CityRestControllerTest {
	
	@Mock
	CityService cityService;
	
	@InjectMocks
	private CityRestController cityRestController;
	
	private City city;
	
	@BeforeEach
	public void setUp() {

		MockitoAnnotations.openMocks(this);

		city = new City("1", "Bucuresti", "B");
	}

	@Test
	public void findAllTest() {

		List<City> cities = new ArrayList<>();
		cities.add(city);
		
		when(cityService.findAllCities()).thenReturn(cities);
		
		List<City> result = cityRestController.findAll();
		
		assertEquals(cities, result);
		verify(cityService,times(1)).findAllCities();
	}
	
	@Test
	public void getCityByIdTest_GoodId_ShouldWork() {
		
		when(cityService.findCityById(city.getId())).thenReturn(city);
		
		City result = cityRestController.getCityById(city.getId());
		
		assertEquals(city, result);
		verify(cityService,times(1)).findCityById(city.getId());
	}
	
	@Test
	public void getCityByIdTest_BadId_ShouldThrowException() {
		
		when(cityService.findCityById("BadId")).thenReturn(null);
		
		assertThrows(ResponseStatusException.class, () -> cityRestController.getCityById("BadId"));
	}
	
	@Test
	public void getCityByCodeTest_GoodId_ShouldWork() {
		
		when(cityService.findCityByCode(city.getCode())).thenReturn(city);
		
		City result = cityRestController.getCityByCode(city.getCode());
		
		assertEquals(city, result);
		verify(cityService,times(1)).findCityByCode(city.getCode());
	}
	
	@Test
	public void getCityByCodeTest_BadId_ShouldThrowException() {
		
		when(cityService.findCityByCode("BadId")).thenReturn(null);
		
		assertThrows(ResponseStatusException.class, () -> cityRestController.getCityByCode("BadId"));
	}

	
	@Test
	public void addCityTest() {
		
		assertEquals(city, cityRestController.addCity(city));
		verify(cityService,times(1)).saveCity(city);
	}
}















