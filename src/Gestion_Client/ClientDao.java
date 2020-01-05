package Gestion_Client;

import java.util.List;

public interface ClientDao {
    public Client find(int id);
    public void create(Client c);
    public void delete(Client c);
    public void update(Client c,String nom, String prenom, String telephone , String ville);
    public List<Client> findAll();
  //  public Client findClient(String key);    
}
