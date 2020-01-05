package Gestion_Paiement;

import java.util.List;

public interface PaiementDAO {

    Paiement find(int id);

    void create(Paiement p);

    void delete(Paiement p);

    void update(Paiement p);

    List<Paiement> findAll(int id);

}
