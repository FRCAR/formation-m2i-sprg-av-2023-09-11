package com.bigcorp.mongodb.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Characteristics {

	/**
	 * A cet attribut sera lié le champ _id dans MongoDB.
	 */
	@Id
	private String id;

	/**
	 * Les autres attributs sont persistés par défaut
	 */
	private String name;
	private Integer weight;
	private LocalDate creationDate;
	private String attributEnPlus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public String getAttributEnPlus() {
		return attributEnPlus;
	}

	public void setAttributEnPlus(String attributEnPlus) {
		this.attributEnPlus = attributEnPlus;
	}

}