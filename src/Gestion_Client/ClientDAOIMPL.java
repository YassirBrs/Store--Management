package Gestion_Client;

import Database.DataConnection;
import UI.Notification;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOIMPL implements ClientDao{
     private DataConnection dc;
     
     PreparedStatement pstm;
     
     public ClientDAOIMPL(){
          dc = DataConnection.getConnection();
     }

    @Override
    public Client find(int id) {
try{
            String url = "SELECT * FROM client WHERE id=?";
            pstm=dc.conn.prepareStatement(url);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if(rs.next() == false ){
                return null;
            }else{
                 Client flag;
                do{
                  flag =new Client(id , rs.getString("nom") , rs.getString("prenom") , rs.getString("telephone"),rs.getString("ville"));
                 }while(rs.next());
                return flag;
            } 
            }catch(Exception eee){
               eee.printStackTrace();
            }
         return null;
    }

    @Override
    public void create(Client c) {
         try{
            String query = "INSERT INTO client (nom, prenom, telephone, ville) VALUES(?,?,?,?)";
            pstm = dc.conn.prepareStatement(query);
            pstm.setString(1, c.getNom());
            pstm.setString(2, c.getPrenom());
            pstm.setString(3, c.getTelephone());
            pstm.setString(4,c.getVille());
            int rows = pstm.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Client c) {
          try{         
            String query = "DELETE FROM client WHERE id = ?";
            pstm = dc.conn.prepareStatement(query);
            pstm.setLong(1, c.getId());
             int rows = pstm.executeUpdate();
        }catch(SQLException e){
              Notification warning = new Notification("Clients");
              warning.setType(Alert.AlertType.ERROR);
              warning.shows("Impossible de supprimer ! exist des Ventes");
        }
    }

    @Override
    public void update(Client c, String nom, String prenom, String telephone, String ville) {
         try{ 
            String query = "UPDATE client SET nom=?,prenom=?,telephone=?,ville=? WHERE id=?";
            pstm = dc.conn.prepareStatement(query);
            pstm.setString(1, nom);
            pstm.setString(2, prenom);
            pstm.setString(3, telephone);
            pstm.setString(4, ville);
            pstm.setInt(5, c.getId());
            pstm.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public List<Client> findAll() {
      List<Client> clients = new ArrayList<>();
         try{     
            String query = "SELECT * FROM client";
            pstm = dc.conn.prepareStatement(query);
            ResultSet rs;
            rs =  pstm.executeQuery();
            while(rs.next()){
                clients.add(new Client(rs.getInt("id") , rs.getString("nom"), rs.getString("prenom") , rs.getString("telephone"),rs.getString("ville")));
            }
            return clients;
         }catch(SQLException e){
            e.printStackTrace();
        }
         return null;
    }
}
