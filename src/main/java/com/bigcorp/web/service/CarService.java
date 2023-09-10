package com.bigcorp.web.service;

import org.springframework.stereotype.Service;

import com.bigcorp.web.model.Car;

@Service
public class CarService {

	public Car getCar(Long id) {
		Car car = new Car();
		car.setId(id);
		car.setName("Jolie voiture");
		return car;
	}

}
