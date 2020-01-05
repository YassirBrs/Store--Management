package Gestion_Produit;

import Gestion_Categorie.Category;
import java.util.*;

public interface ProductDAO {
    Product find(int id);
    void create(Product p);
    void delete(Product p);
    void update(Product p, String s, double price, Category categorie);
    List<Product> findAll();
    List<Product> findAll(String key);
}
