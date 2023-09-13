package com.bigcorp.data.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bigcorp.data.model.Product;

public interface ProductDao extends CrudRepository<Product, Integer> {

	List<Product> findByNumeroAndNom(Integer numero, String nom);
	
}