package com.bigcorp.batch.correction.steppart;

import org.springframework.batch.item.ItemProcessor;

import com.bigcorp.batch.correction.bean.Livraison;
import com.bigcorp.batch.correction.bean.ProduitEnStock;

public class ProduitEnStockProcessor implements ItemProcessor<ProduitEnStock, Livraison> {

	@Override
	public Livraison process(ProduitEnStock item) throws Exception {
		Livraison livraison = new Livraison();
		livraison.setNomProduit(item.getDesignation());
		livraison.setMasse(item.getMasse());
		livraison.setCommande(item.getCommande());
		System.out.println("Je traite la livraison : " + livraison.getNomProduit());
		return livraison;
	}

}
