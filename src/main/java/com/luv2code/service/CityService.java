package com.luv2code.service;

import java.util.List;

import com.luv2code.entity.City;

public interface CityService {

	public void saveCity(City theCity);
	
	public City findCityById(String theId);
	
	public City findCityByCode(String theCode);
	
	public List<City> findAllCities();
}
