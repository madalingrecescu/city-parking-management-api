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
import com.luv2code.service.CityService;

@RestController
@RequestMapping("/api")
public class CityRestController {

	private CityService cityService;

	@Autowired
	public CityRestController(CityService theCityService) {
		cityService = theCityService;
	}

	// expose "/cities" endpoint and return a list of cities
	@GetMapping("/cities")
	public List<City> findAll() {
		return cityService.findAllCities();
	}

	// expose "/cities/{cityId}" endpoint and return a single city by id
	@GetMapping("/cities/{cityId}")
	public City getCityById(@PathVariable String cityId) {
		City theCity = cityService.findCityById(cityId);

		if (theCity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"City id not found - " + cityId);
		}
		return theCity;
	}

	// expose "/cities/code/{code}" endpoint and return a single city by code
	@GetMapping("/cities/code/{code}")
	public City getCityByCode(@PathVariable String code) {
		City theCity = cityService.findCityByCode(code);

		if (theCity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"City code not found - " + code);
		}
		return theCity;
	}

	// add mapping for POST /cities - add new city
	@PostMapping("/cities")
	public City addCity(@RequestBody City theCity) {
		
	    cityService.saveCity(theCity);
	    return theCity;
	    
	}
	

}