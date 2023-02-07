package com.luv2code.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.luv2code.dao.CityRepository;
import com.luv2code.entity.City;

@Service
public class CityServiceImpl implements CityService{
	
	private CityRepository cityRepository;

	@Autowired
	public CityServiceImpl(CityRepository theCityRepository) {
		
		cityRepository = theCityRepository;
	}
	
	@Override
	public void saveCity(City theCity) {
		Optional<City> result = cityRepository.findById(theCity.getId());
		
		
		if (result.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"City id is already used - " + theCity.getId());
		}
		
		cityRepository.save(theCity);
	}

	@Override
	public City findCityById(String theId) {

		Optional<City> result = cityRepository.findById(theId);
		
		City theCity = null;
		
		if(result.isPresent()) {
			theCity = result.get();
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Did not find the city id - " + theId);
		}
		return theCity;
	}

	@Override
	public City findCityByCode(String theCode) {
		
		Optional<City> result = cityRepository.findByCode(theCode);
		
		City theCity = null;
		
		if(result.isPresent()) {
			theCity = result.get();
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Did not find the city id - " + theCode);
		}
	    return theCity;
	}


	@Override
	public List<City> findAllCities() {
		
		return cityRepository.findAll();
	}

}
