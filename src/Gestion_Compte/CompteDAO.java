package Gestion_Compte;

public interface CompteDAO {
    Compte find(int id);
    void update(Compte c,double newSolde);
}
