
package Gestion_Categorie;

import java.util.List;

public interface CategorieDAO {
    Categorie find(int id);
    void create(Categorie c);
    void delete(Categorie c);
    void update(Categorie c, String nom, String desc);
    List<Categorie> findAll();
    Categorie findCate(String key);
}
