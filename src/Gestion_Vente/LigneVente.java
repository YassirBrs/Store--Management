package Gestion_Vente;

import Gestion_Produit.Produit;

import java.io.Serializable;


public class LigneVente implements Serializable {
    
    private int id;
    private Vente vente;
    private Produit produit;
    private int qte;
    private double subtotal;
    private double prix;

    public LigneVente(int id, Vente vente, Produit produit, int qte) {
        this.id = id;
        this.vente = vente;
        this.produit = produit;
        this.qte = qte;
        this.prix = produit.getPrix();
        this.subtotal = produit.getPrix() * qte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    public void updatePrix(){
        this.prix = this.produit.getPrix();
    }

    public double getPrix() {
        return prix;
    }
    
}
