package com.bigcorp.batch.ressort.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.bigcorp.batch.ressort.batch.Ressort;

public class RessortProcessor implements ItemProcessor<Ressort, Ressort> {

	public Ressort process(Ressort ressort) {
		ressort.setName("coucou-" + ressort.getName());
		return ressort;
	}

}