package com.bigcorp.web.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bigcorp.web.model.Car;
import com.bigcorp.web.service.CarService;

@RestController
public class CarController {

	@Autowired
	private CarService carService;

	@GetMapping("/cars/{id}")
	public Car getCarById(@PathVariable("id") Long id) {
		return this.carService.getCar(id);
	}

}