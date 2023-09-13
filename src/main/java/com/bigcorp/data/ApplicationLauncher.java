package com.bigcorp.data;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bigcorp.data.configuration.SpringDataConfiguration;
import com.bigcorp.data.dao.CategoryDao;
import com.bigcorp.data.dao.ProductDao;
import com.bigcorp.data.model.Category;
import com.bigcorp.data.model.Product;

/**
 * Lance une application qui démarre un contexte Spring. Ce contexte est
 * configuré avec SpringDataConfiguration. Or, cette configuration active les
 * capacités de Spring Data JPA, ce qui permet de se servir des interfaces qui
 * héritent de CrudRepository.
 */
public class ApplicationLauncher {

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
				SpringDataConfiguration.class)) {

			Category categorie1 = new Category();
			categorie1.setNom("Nouveau nom");

			// Sauvegarde d'une catégorie en base
			CategoryDao categoryDao = appContext.getBean(CategoryDao.class);
			categoryDao.save(categorie1);

			// Récupération d'une entité par son identifiant
			Category categorieQuiVientDeLaBase = categoryDao.findById(categorie1.getId()).get();
			System.out.println(categorieQuiVientDeLaBase.getNom());

			// Utilisation d'une query method
			List<Category> categoriesParNom = categoryDao.autreMethodeDeRechercheAvecArobaseQuery("Nouveau nom");
			for (Category category : categoriesParNom) {
				System.out.println(
						"J'ai retrouvé la catégorie avec l'id : "
								+ category.getId()
								+ " à partir du nom  : "
								+ category.getNom());
			}

			// Sauvegarde d'un product en base
			ProductDao productDao = appContext.getBean(ProductDao.class);
			Product product1 = new Product();
			product1.setNom("Super produit");
			product1.setNumero(5);
			productDao.save(product1);

			// Utilisation d'une query method
			List<Product> products = productDao.findByNumeroAndNom(5, "Super produit");
			for (Product product : products) {
				System.out.println(
						"J'ai retrouvé le produit avec l'id : "
								+ product.getId()
								+ " à partir du nom  : "
								+ product.getNom()
								+ " et du numéro : "
								+ product.getNumero());
			}
		}

	}

}