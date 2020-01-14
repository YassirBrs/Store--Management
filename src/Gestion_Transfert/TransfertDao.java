package Gestion_Transfert;

import java.util.List;

public interface TransfertDao {
    Transfert find(int id);
    void create(Transfert c);
    List<Transfert> findAll();
}
