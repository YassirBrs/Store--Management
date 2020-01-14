package Gestion_Transfert;

import Database.DataConnection;
import Gestion_Categorie.Categorie;
import Gestion_Paiement.Paiement;
import Gestion_Paiement.PaiementDAO;
import Gestion_Produit.Produit;
import UI.Notification;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransfertDAOIMPL implements TransfertDao {
    private DataConnection dc;

    PreparedStatement pstm;

    public TransfertDAOIMPL() {
        dc = DataConnection.getConnection();
    }

    @Override
    public Transfert find(int id) {
        try {
            String url = "SELECT * FROM transfert WHERE id=?";
            pstm = dc.conn.prepareStatement(url);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next() == false) {
                return null;
            } else {
                Transfert trs;
                do {
                    trs = new Transfert(id, rs.getString("nom"), rs.getString("prenom"), rs.getString("rib"), rs.getString("bank"), rs.getDouble("montant"), rs.getString("date"));
                } while (rs.next());
                return trs;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Transfert c) {
        try {
            String query = "INSERT INTO transfert (nom,prenom,rib,bank,montant,date) VALUES(?,?,?,?,?,?)";
            pstm = dc.conn.prepareStatement(query);
            pstm.setString(1, c.getNom());
            pstm.setString(2, c.getPrenom());
            pstm.setString(3, c.getRib());
            pstm.setString(4, c.getBank());
            pstm.setDouble(5, c.getMontant());
            pstm.setString(6, c.getDate());
            int rows = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Transfert> findAll() {
        List<Transfert> transferts = new ArrayList<>();
        try {
            String query = "SELECT * FROM transfert";
            pstm = dc.conn.prepareStatement(query);
            ResultSet rs;
            rs = pstm.executeQuery();
            while (rs.next()) {
                transferts.add(new Transfert(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"),rs.getString("rib"),rs.getString("bank"),rs.getDouble("montant"), rs.getString("date")));
                for (Transfert t:transferts)t.toString();
            }
            return transferts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
