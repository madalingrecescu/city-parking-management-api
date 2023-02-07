package com.luv2code.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.luv2code.dao.CityRepository;
import com.luv2code.entity.City;
import com.luv2code.service.CityService;
import com.luv2code.service.CityServiceImpl;


class CityServiceImplTest {
	
	private City city;
	
	@Mock
	CityRepository cityRepository;
	
	@InjectMocks
	CityServiceImpl cityServiceImpl;


	@BeforeEach
	public void setUp() {
		
		MockitoAnnotations.openMocks(this);
		
		city = new City("1", "Bucuresti", "B");
	}
	
	@Test
	public void saveCity_CityNotFound_ShouldSaveCity() {
		
		when(cityRepository.findById(city.getId())).thenReturn(Optional.empty());
		
		
		cityServiceImpl.saveCity(city);
		
		verify(cityRepository, times(1)).save(city);

		
	}
	
	@Test
	public void saveCity_CityFound_ShouldThrowException() {
		
		when(cityRepository.findById(city.getId())).thenReturn(Optional.of(city));
		
		assertThrows(ResponseStatusException.class, () -> cityServiceImpl.saveCity(city));
		
		verify(cityRepository, times(0)).save(city);
	}
	
	@Test
	public void findCityById_CityFound_ShouldReturnCity(){
		
		when(cityRepository.findById(city.getId())).thenReturn(Optional.of(city));
		
		City result = cityServiceImpl.findCityById(city.getId());
		
		assertEquals(city, result);
		
		verify(cityRepository, times(1)).findById(city.getId());
	}

	@Test
	public void findCityById_CityNotFound_ShouldThrowException() {
		
		when(cityRepository.findById("2")).thenReturn(Optional.empty());
		
		assertThrows(ResponseStatusException.class, () -> cityServiceImpl.findCityById(city.getId()));
		
		verify(cityRepository, times(0)).findById("2");
	}
	
	@Test
	public void findCityByCode_CityFound_ShouldReturnCity(){
		
		when(cityRepository.findByCode(city.getCode())).thenReturn(Optional.of(city));
		
		City result = cityServiceImpl.findCityByCode(city.getCode());
		
		assertEquals(city, result);
		
		verify(cityRepository, times(1)).findByCode(city.getCode());
	}

	@Test
	public void findCityByCode_CityNotFound_ShouldThrowException() {
		
		when(cityRepository.findByCode("2")).thenReturn(Optional.empty());
		
		assertThrows(ResponseStatusException.class, () -> cityServiceImpl.findCityById(city.getId()));
		
		verify(cityRepository, times(0)).findByCode("asd");
	}
	
	@Test
	public void findAllCitiesTest() {
		List<City> cities = new ArrayList<>();
		cities.add(city);
		cities.add(new City("2", "oras", "or"));
		
		when(cityRepository.findAll()).thenReturn(cities);
		
		assertEquals(cities, cityServiceImpl.findAllCities());
	}
}






