package com.bigcorp.batch.ressort.service;

import org.springframework.stereotype.Component;

@Component
public class RessortService {

	public void compresse() {
		System.out.println("Je suis compressé !");
	}

	public void detend() {
		System.out.println("Je suis détendu !");
	}

}
