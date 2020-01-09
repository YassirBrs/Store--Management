package Gestion_Produit;

import Gestion_Categorie.Categorie;
import java.util.*;

public interface ProduitDAO {
    Produit find(int id);
    Produit findCat(int idCat);
    void create(Produit p);
    void delete(Produit p);
    void update(Produit p, String s, double price, Categorie categorie);
    List<Produit> findAll();
    List<Produit> findAll(String key);
}
