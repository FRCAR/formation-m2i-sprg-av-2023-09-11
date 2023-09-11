package com.bigcorp.batch.correction.steppart;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import com.bigcorp.batch.correction.bean.Livraison;

public class LivraisonItemWriter implements ItemWriter<Livraison> {

	@Override
	public void write(Chunk<? extends Livraison> chunk) throws Exception {
		for(Livraison livraison : chunk.getItems()) {
			System.out.println("Je livre : " + livraison.getNomProduit());
		}

	}

}
