package com.bigcorp.web.restcontroller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bigcorp.data.model.Category;

public class CategoryController {

	@RequestMapping("/categories/{id}")
	public Category showUserForm(@PathVariable(name = "id", required = false) Category category) {
		if (category == null) {
			System.out.println("aucune catégorie trouvée");
		} else {
			System.out.println("Catégorie : " + category.getNom());
		}
		return category;
	}

}