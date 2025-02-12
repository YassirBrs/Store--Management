package Gestion_Vente;

import Gestion_Client.Client;
import Gestion_Client.ClientDAOIMPL;
import Database.DataConnection;
import UI.Notification;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VenteDAOIMPL implements VenteDAO {

    private DataConnection dc;
    PreparedStatement pstm;
    ClientDAOIMPL dao;

    public VenteDAOIMPL() {
        dc = DataConnection.getConnection();
        dao = new ClientDAOIMPL();
    }

    @Override
    public Vente find(int id) {
        try {
            String url = "SELECT vente.id as id, nom, prenom, date, id_client, SUM(prix*qte) as total,imprimer"
                    + " FROM client INNER JOIN vente ON client.id=id_client"
                    + " LEFT JOIN lignevente ON vente.id=id_vente"
                    + " LEFT JOIN produit ON produit.id=id_produit"
                    + " WHERE vente.id = ?"
                    + " GROUP BY vente.id";

            pstm = dc.conn.prepareStatement(url);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next() == false) {
                return null;
            } else {
                Vente flag;
                do {
                    Client cli = dao.find(rs.getInt("id_client"));
                    flag = new Vente(id, cli, rs.getString("date"));
                    flag.setTotal(rs.getDouble("total"));
                    flag.setImprimer(rs.getString("imprimer"));
                } while (rs.next());
                return flag;
            }

        } catch (Exception eee) {
            eee.printStackTrace();
        }
        return null;

    }

    @Override
    public void create(Vente p) {
        try {

            String query = "INSERT INTO vente (id_client, date) VALUES(?,?)";
            pstm = dc.conn.prepareStatement(query);
            pstm.setInt(1, p.getClient().getId());
            pstm.setString(2, p.getDate());
            int rows = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Vente p) {
        try {
            String query = "DELETE FROM vente WHERE id = ?";
            pstm = dc.conn.prepareStatement(query);
            pstm.setInt(1, p.getId());
            int rows = pstm.executeUpdate();
        } catch (SQLException e) {
            Notification warning = new Notification("Ventes");
            warning.setType(Alert.AlertType.ERROR);
            warning.shows("Impossible de supprimer ! exist des lignes de commandes ou des paiement");
        }
    }

    @Override
    public void update(Vente p) {
        try {
            String query = "UPDATE vente SET id_client=?,date=? WHERE id=?";
            pstm = dc.conn.prepareStatement(query);
            pstm.setInt(1, p.getClient().getId());
            pstm.setString(2, p.getDate());
            pstm.setInt(3, p.getId());
            int x = pstm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateImprimer(int id, String value) {
        try {
            String query = "UPDATE vente SET imprimer=? WHERE id=?";
            pstm = dc.conn.prepareStatement(query);
            pstm.setString(1, value);
            pstm.setInt(2, id);
            int x = pstm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Vente> findAll() {

        List<Vente> ventes = new ArrayList<>();
        try {
            String query = "SELECT vente.id as id, nom, prenom, date, id_client, SUM(prix*qte) as total,imprimer"
                    + " FROM client INNER JOIN vente ON client.id=id_client"
                    + " LEFT JOIN lignevente ON vente.id=id_vente"
                    + " LEFT JOIN produit ON produit.id=id_produit"
                    + " GROUP BY vente.id";
            pstm = dc.conn.prepareStatement(query);
            ResultSet rs;
            rs = pstm.executeQuery();
            Vente v;
            while (rs.next()) {
                v = new Vente(rs.getInt("id"), dao.find(rs.getInt("id_client")), rs.getString("date"));
                v.setTotal(rs.getDouble("total"));
                v.setImprimer(rs.getString("imprimer"));
                ventes.add(v);
            }
            return ventes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

}
