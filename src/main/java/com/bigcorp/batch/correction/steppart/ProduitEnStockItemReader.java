package com.bigcorp.batch.correction.steppart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.bigcorp.batch.correction.bean.ProduitEnStock;

public class ProduitEnStockItemReader implements ItemReader<ProduitEnStock> {

	private List<ProduitEnStock> produits = new ArrayList<>();

	private int index = 0;

	public ProduitEnStockItemReader() {
		ProduitEnStock produit1 = new ProduitEnStock();
		produit1.setDesignation("Casserole");
		this.produits.add(produit1);

		ProduitEnStock produit2 = new ProduitEnStock();
		produit2.setDesignation("Fourchette");
		this.produits.add(produit2);
	}

	@Override
	public ProduitEnStock read() throws Exception, UnexpectedInputException, ParseException,
			NonTransientResourceException {
		if (this.index == this.produits.size()) {
			return null;
		}
		return this.produits.get(index++);
	}

}
