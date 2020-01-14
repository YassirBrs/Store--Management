package Gestion_Categorie;

import Database.DataConnection;
import Gestion_Produit.Produit;
import UI.Notification;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAOIMPL implements CategorieDAO {

    private DataConnection dc;

    PreparedStatement pstm;

    public CategorieDAOIMPL() {
        dc = DataConnection.getConnection();

    }

    @Override
    public Categorie find(int id) {
        try {
            String url = "SELECT * FROM categorie WHERE id=?";
            pstm = dc.conn.prepareStatement(url);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next() == false) {
                return null;
            } else {
                Categorie c;
                do {
                    c = new Categorie(id, rs.getString("nom"), rs.getString("description"));
                } while (rs.next());
                return c;
            }
        } catch (Exception eee) {
            eee.printStackTrace();
        }
        return null;

    }

    @Override
    public void create(Categorie ca) {
        try {
            String query = "INSERT INTO categorie (nom, description) VALUES(?,?)";
            pstm = dc.conn.prepareStatement(query);
            pstm.setString(1, ca.getNom());
            pstm.setString(2, ca.getDescription());
            int rows = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Categorie ca) {
        try {
            String query = "DELETE FROM categorie WHERE id = ?";
            pstm = dc.conn.prepareStatement(query);
            pstm.setLong(1, ca.getId());
            int rows = pstm.executeUpdate();
        } catch (SQLException e) {
            Notification warning = new Notification("catégories");
            warning.setType(Alert.AlertType.ERROR);
            warning.shows("Impossible de supprimer ! exist des produits");
        }
    }

    @Override
    public void update(Categorie ca, String nom, String description) {
        try {
            String query = "UPDATE categorie SET nom=?,description=? WHERE id=?";
            pstm = dc.conn.prepareStatement(query);
            pstm.setString(1, nom);
            pstm.setString(2, description);
            pstm.setLong(3, ca.getId());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Categorie> findAll() {
        List<Categorie> categories = new ArrayList<>();
        try {
            String query = "SELECT * FROM categorie";
            pstm = dc.conn.prepareStatement(query);
            ResultSet rs;
            rs = pstm.executeQuery();
            while (rs.next()) {
                categories.add(new Categorie(rs.getInt("id"), rs.getString("nom"), rs.getString("description")));
            }
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Categorie findProduct(int id) {
        List<Categorie> res = findAll();
        for (Categorie c : res) {
            for (Produit p : c.getProducts()) {
                if (p.getId() == id) {
                    return c;
                }
            }
        }
        return null;
    }

    @Override
    public Categorie findCate(String key) {
        try {
            String url = "SELECT * FROM categorie WHERE nom=?";
            pstm = dc.conn.prepareStatement(url);
            pstm.setString(1, key);
            ResultSet rs = pstm.executeQuery();
            if (rs.next() == false) {
                return null;
            } else {
                Categorie c;
                do {
                    c = new Categorie(rs.getInt("id"), rs.getString("nom"), rs.getString("description"));
                } while (rs.next());
                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
