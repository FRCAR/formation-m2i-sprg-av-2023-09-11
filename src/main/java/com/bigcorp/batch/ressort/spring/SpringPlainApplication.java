package com.bigcorp.batch.ressort.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bigcorp.batch.ressort.service.AmortisseurService;
import com.bigcorp.batch.ressort.service.RessortService;

public class SpringPlainApplication {

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext()) {
			appContext.scan("com.bigcorp");
			appContext.refresh();

			RessortService ressortService = (RessortService) appContext.getBean("ressortService");
			ressortService.compresse();

			AmortisseurService amortisseurService = (AmortisseurService) appContext.getBean("amortisseurService");
			amortisseurService.passeUneBosse();
		}
	}

}
