package com.bigcorp.batch.ressort.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AmortisseurService {

	@Autowired
	private RessortService ressortService;

	public void passeUneBosse() {
		this.ressortService.compresse();
		this.ressortService.detend();
		System.out.println("J'ai passé la bosse.");
	}

}
