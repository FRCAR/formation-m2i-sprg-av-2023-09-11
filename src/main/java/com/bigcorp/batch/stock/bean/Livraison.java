package com.bigcorp.batch.stock.bean;

public class Livraison {

	private String nomProduit;
	private String commande;

	// Masse en grammes
	private Integer masse;

	public String getNomProduit() {
		return nomProduit;
	}

	public void setNomProduit(String nomProduit) {
		this.nomProduit = nomProduit;
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
