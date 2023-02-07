package com.luv2code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class CityParkingManagement {

	public static void main(String[] args) {
		SpringApplication.run(CityParkingManagement.class, args);
	}

}
