package com.bigcorp.data;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bigcorp.data.configuration.SpringDataConfiguration;
import com.bigcorp.data.model.Category;

public class ApplicationLauncher {

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext appContext 
				= new AnnotationConfigApplicationContext(SpringDataConfiguration.class)) {
			Category categorie1 = new Category();
			categorie1.setNom("Catégorie 3");


		}
	
	}

}