package Gestion_Produit;

import Gestion_Categorie.Categorie;

import java.io.Serializable;

public class Produit implements Serializable {
    private int id;
    private String designation;
    private double prix;
    private Categorie catid;

    public Categorie getCatid() {
        return catid;
    }

    public void setCatid(Categorie categorie) {
        this.catid = categorie;
    }
    
    
    public Produit(int d , String designation , double prix, Categorie categorie){
        this.id = d;
        this.designation = designation;
        this.prix = prix;
        this.catid = categorie;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getDesignation(){
        return this.designation;
    }
    
    public double getPrix(){
        return this.prix;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setDesignation(String des){
        this.designation = des;
    }
    
    public void setPrix(double prix){
        this.prix = prix;
    }

    @Override
    public String toString() {
        return designation;
    }
    
}

