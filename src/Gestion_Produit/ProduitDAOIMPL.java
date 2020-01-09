package Gestion_Produit;

import Gestion_Categorie.Categorie;
import Gestion_Categorie.CategorieDAOIMPL;
import Database.DataConnection;
import UI.Notification;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAOIMPL implements ProduitDAO {
     private DataConnection dc;
     PreparedStatement pstm;
     CategorieDAOIMPL dao;

    @Override
    public Produit findCat(int idCat) {
        return null;
    }

    public ProduitDAOIMPL(){
         dc =DataConnection.getConnection();
         dao = new CategorieDAOIMPL();
     }

    @Override
    public Produit find(int id) {
 
           
            try{
                String url = "SELECT * FROM produit WHERE id=?";
            
            pstm=dc.conn.prepareStatement(url);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            // recherche de la catégorie
            
            if(rs.next() == false ){
                return null;
            }else{
                 Produit flag;
                do{
                  Categorie res = dao.find(rs.getInt("catid"));
                  flag =new Produit(id , rs.getString("designation") , rs.getDouble("prix"), res);
                 }while(rs.next());
                return flag;
            }
            }catch(Exception eee){
               eee.printStackTrace();
            }
         return null;
       
    }

    @Override
    public void create(Produit p) {
       
    try{      
        
            
            String query = "INSERT INTO produit (designation, prix, catid) VALUES(?,?,?)";
            pstm = dc.conn.prepareStatement(query);
            pstm.setString(1, p.getDesignation());
            pstm.setDouble(2, p.getPrix());
            int id = p.getCatid().getId();
            pstm.setInt(3, id);
             int rows = pstm.executeUpdate();
             System.out.println("Produit inséré !");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Produit p) {
        try{         
            String query = "DELETE FROM produit WHERE id = ?";
            pstm = dc.conn.prepareStatement(query);
            pstm.setInt(1, p.getId());
             int rows = pstm.executeUpdate();
        }catch(SQLException e){
            Notification warning = new Notification("Produits");
            warning.setType(Alert.AlertType.ERROR);
            warning.shows("Impossible de supprimer ! exist une ligne de commande associer");
        }
    }

    @Override
    public void update(Produit p, String des , double price, Categorie categorie) {
        try{ 
            String query = "UPDATE produit SET designation=?,prix=?,catid=? WHERE id=?";
            pstm = dc.conn.prepareStatement(query);
            pstm.setString(1, des);
            pstm.setDouble(2, price);
            pstm.setInt(3, categorie.getId());
            pstm.setInt(4, p.getId());
            int x = pstm.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    private Categorie findCategory(int id){
        String query= "SELECT * FROM categorie WHERE id = ?";
        try{
        pstm = dc.conn.prepareStatement(query);
        pstm.setInt(1,id);   
        ResultSet rs = pstm.executeQuery();
        if(rs.next()){
         
        return new Categorie(rs.getInt("id") , rs.getString("nom") , rs.getString("description"));
        }else{
            return null;
        }
        }catch(SQLException dd){
            dd.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<Produit> findAll() {
       
       List<Produit> produits = new ArrayList<>();
         try{     
            String query = "SELECT * FROM produit";
            pstm = dc.conn.prepareStatement(query);
            ResultSet rs;
            rs =  pstm.executeQuery();
            while(rs.next()){
                Categorie c= findCategory(rs.getInt("catid"));
                produits.add(new Produit(rs.getInt("id") , rs.getString("designation"), rs.getDouble("prix"),c));
            }
            return produits;
         }catch(SQLException e){
            e.printStackTrace();
        }
         return null;
        
    }

    @Override
    public List<Produit> findAll(String key) {
        ResultSet rs = null; 
       List<Produit> produits = null;
         try{         
            produits = new ArrayList<>();
            String query = "SELECT * FROM produit WHERE designation= ?";
            
            pstm = dc.conn.prepareStatement(query);
            pstm.setString(1, key);
            rs =  pstm.executeQuery();
            while(rs.next()){
                Categorie c = dao.findProduct(rs.getInt("id"));
                produits.add(new Produit(rs.getInt("id") , rs.getString("designation"), rs.getDouble("prix"),c));
            }
         }catch(SQLException e){
            e.printStackTrace();
        }
         
        if(produits.size() !=0){
            return produits;
        }else{
            return null;
        }
    }
    public List<Categorie> getCategories(){
        List<Categorie> categories =  dao.findAll();
        return categories;
    }
     
    public Categorie findCate(String key) {
             
         try{      
            String query = "SELECT * FROM categorie WHERE nom=?";
            
            pstm = dc.conn.prepareStatement(query);
            pstm.setString(1, key);
            ResultSet rs =  pstm.executeQuery();
            if(!rs.next()){
             
            Categorie categorie = new Categorie(rs.getInt("id") , rs.getString("nom"), rs.getString("description"));
               
            return categorie;   
            }
         }catch(SQLException e){
            e.printStackTrace();
        }
         
        return null;
    }
        public Categorie getProductCategory(int id){
            String query = "SELECT category.nom FROM produit inner join category on produit.id_category = category.id WHERE produit.id=?";
            try{
              pstm = dc.conn.prepareStatement(query);
              ResultSet rs = pstm.executeQuery();
              return new Categorie(rs.getInt("id") , rs.getString("nom"),rs.getString("description"));
            }catch(SQLException dd){
                dd.printStackTrace();
            }
            return null;
        }
     
     
}
