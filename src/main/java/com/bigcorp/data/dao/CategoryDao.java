package com.bigcorp.data.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bigcorp.data.model.Category;

public interface CategoryDao extends CrudRepository<Category, Integer> {

	List<Category> findByNom(String nom);
	
	@Query("from Category where nom = :nom")
	List<Category> autreMethodeDeRechercheAvecArobaseQuery(@Param("nom") String nom);

}