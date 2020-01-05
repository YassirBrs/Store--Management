package Gestion_Client;

import java.util.List;

public interface ClientDao {
    Client find(int id);
    void create(Client c);
    void delete(Client c);
    void update(Client c, String nom, String prenom, String telephone, String ville);
    List<Client> findAll();
  //  public Client findClient(String key);    
}
