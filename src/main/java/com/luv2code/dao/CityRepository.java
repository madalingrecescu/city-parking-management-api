package com.luv2code.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luv2code.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, String> {

	Optional<City> findByCode(String code);
}
