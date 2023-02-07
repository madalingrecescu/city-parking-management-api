package com.luv2code.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luv2code.entity.ParkingFacility;

@Repository
public interface ParkingFacilityRepository extends JpaRepository<ParkingFacility, String> {

	List<ParkingFacility> findByCityId(String theCityId);

}
