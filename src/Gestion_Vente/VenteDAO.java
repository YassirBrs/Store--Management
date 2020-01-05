package Gestion_Vente;

import java.util.*;

public interface VenteDAO {

    Vente find(int id);

    void create(Vente p);

    void delete(Vente p);

    void update(Vente p);

    List<Vente> findAll();
}
