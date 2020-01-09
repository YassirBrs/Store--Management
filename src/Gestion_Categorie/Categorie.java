package Gestion_Categorie;

import Gestion_Produit.Produit;

import java.util.List;

public class Categorie {
    private int id;
    private String nom;
    private String description;
    private List<Produit> produits;
    
    public Categorie(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.produits = getProducts();
    }
    
    public List<Produit> getProducts(){
         return this.produits;
    }
    
    public void setProduits(List<Produit> produits){
        this.produits = produits;
    }
    
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String toString(){
        return nom;
    }
     
}
