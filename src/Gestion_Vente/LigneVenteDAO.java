package Gestion_Vente;

import java.util.List;

public interface LigneVenteDAO {

    LigneVente find(int id);

    void create(LigneVente p);

    void delete(LigneVente p);

    void update(LigneVente p);

    List<LigneVente> findAll();

    List<LigneVente> findAll(int id);

    List<LigneVente> search(int id);
    
}
