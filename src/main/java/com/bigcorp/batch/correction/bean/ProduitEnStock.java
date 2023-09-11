package com.bigcorp.batch.correction.bean;

public class ProduitEnStock {

	private String designation;
	private String commande;

	// Masse en grammes
	private Integer masse;

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getCommande() {
		return commande;
	}

	public void setCommande(String commande) {
		this.commande = commande;
	}

	public Integer getMasse() {
		return masse;
	}

	public void setMasse(Integer masse) {
		this.masse = masse;
	}

}
