package com.bigcorp.batch.correction.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.bigcorp.batch.correction.bean.ProduitEnStock;

/**
 * Mappe un FieldSet avec une instance de ProduitEnStock. Utilisé par un
 * ItemReader
 */
public class ProduitEnStockFieldSetMapper implements FieldSetMapper<ProduitEnStock> {

	public ProduitEnStock mapFieldSet(FieldSet fieldSet) throws BindException {
		ProduitEnStock produitEnStock = new ProduitEnStock();

		produitEnStock.setDesignation(fieldSet.readString("designation"));
		produitEnStock.setCommande(fieldSet.readString("commande"));
		try {
			int masse = fieldSet.readInt("masse");
			produitEnStock.setMasse(masse);
		} catch (IllegalArgumentException iae) {
			throw new BindException(fieldSet, "masse");
		}

		return produitEnStock;
	}

}
