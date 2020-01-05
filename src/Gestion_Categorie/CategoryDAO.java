
package Gestion_Categorie;

import java.util.List;

public interface CategoryDAO {
    Category find(int id);
    void create(Category c);
    void delete(Category c);
    void update(Category c, String nom, String desc);
    List<Category> findAll();
    Category findCate(String key);
}
