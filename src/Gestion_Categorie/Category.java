package Gestion_Categorie;

import Gestion_Produit.Product;

import java.util.List;

public class Category {
    private int id;
    private String nom;
    private String description;
    private List<Product> produits;
    
    public Category(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.produits = getProducts();
    }
    
    public List<Product> getProducts(){
         return this.produits;
    }
    
    public void setProduits(List<Product> produits){
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
