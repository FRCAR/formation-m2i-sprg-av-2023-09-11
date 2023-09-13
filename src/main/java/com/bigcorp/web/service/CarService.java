package com.bigcorp.web.service;

import java.util.Collection;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bigcorp.web.model.Car;

@Service
public class CarService {

	@Secured("ROLE_CAR_READER")
	public Car getCar(Long id) {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		authorities.stream().forEach(System.out::println);
		
		Car car = new Car();
		car.setId(id);
		car.setName("Jolie voiture");
		return car;
	}

}
