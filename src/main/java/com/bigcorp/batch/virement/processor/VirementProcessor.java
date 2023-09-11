package com.bigcorp.batch.virement.processor;

import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.ItemProcessor;

import com.bigcorp.batch.virement.bean.DemandeVirement;
import com.bigcorp.batch.virement.bean.Virement;

public class VirementProcessor implements ItemProcessor<DemandeVirement, Virement> {

	@Override
	public Virement process(DemandeVirement item) throws Exception {
		// Code de transformation
		Virement virement = new Virement();
		virement.setAmount(item.getAmount());
		virement.setDate(item.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		virement.setTime(item.getDate().format(DateTimeFormatter.ofPattern("hh:mm")));
		virement.setName(item.getLabel());
		virement.setFrom(item.getAccountFrom());
		virement.setTo(item.getAccountTo());

		// Code fonctionnel
		System.out.println("J'ai traité le virement avec le libellé " + item.getLabel());

		return virement;
	}

}
