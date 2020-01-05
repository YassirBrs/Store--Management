package Gestion_Paiement;

import Database.DataConnection;
import Gestion_Vente.Vente;
import Gestion_Vente.VenteDAOIMPL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaiementDAOIMPL implements PaiementDAO {

    private DataConnection dc;
    PreparedStatement pstm;
    VenteDAOIMPL daoVente;

    public PaiementDAOIMPL() {
        dc = DataConnection.getConnection();
        daoVente = new VenteDAOIMPL();
    }

    @Override
    public Paiement find(int id) {
        try {
            String url = "SELECT *, paiement.id as id FROM"
                    + " vente INNER JOIN paiement ON vente.id=id_vente"
                    + " WHERE paiement.id = ?";

            pstm = dc.conn.prepareStatement(url);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next() == false) {
                return null;
            } else {
                Paiement flag;
                do {
                    Vente v = daoVente.find(rs.getInt("id_vente"));
                    flag = new Paiement(id, v, rs.getDouble("montant"), rs.getString("paiement.date"), rs.getString("status"),
                            rs.getString("dateEffet"), rs.getString("type"));
                } while (rs.next());
                return flag;
            }

        } catch (Exception eee) {
            eee.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Paiement p) {
        try {

            String query = "INSERT INTO paiement (id_vente, montant, date, status, dateEffet, type) VALUES(?,?,?,?,?,?)";
            pstm = dc.conn.prepareStatement(query);
            pstm.setInt(1, p.getVente().getId());
            pstm.setDouble(2, p.getMontant());
            pstm.setString(3, p.getDate());
            pstm.setString(4, p.getStatus());
            pstm.setString(5, p.getDateEffet());
            pstm.setString(6, p.getType());
            int rows = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Paiement p) {
        try {
            String query = "DELETE FROM paiement WHERE id = ?";
            pstm = dc.conn.prepareStatement(query);
            pstm.setInt(1, p.getId());
            int rows = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Paiement p) {
        try {
            String query = "UPDATE paiement SET id_vente=?,montant=?,date=?,status=?,dateEffet=?,type=? WHERE id=?";
            pstm = dc.conn.prepareStatement(query);
            pstm.setInt(1, p.getVente().getId());
            pstm.setDouble(2, p.getMontant());
            pstm.setString(3, p.getDate());
            pstm.setString(4, p.getStatus());
            pstm.setString(5, p.getDateEffet());
            pstm.setString(6, p.getType());
            pstm.setInt(7, p.getId());
            int x = pstm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Paiement> findAll(int id) {
        List<Paiement> list = new ArrayList<>();
        try {
            String query = "SELECT *, paiement.id as id FROM"
                    + " vente INNER JOIN paiement ON vente.id=id_vente"
                    + " WHERE id_vente=?";
            pstm = dc.conn.prepareStatement(query);
            pstm.setInt(1, id);
            ResultSet rs;
            rs = pstm.executeQuery();
            Paiement v;
            while (rs.next()) {
                v = new Paiement(rs.getInt("paiement.id"), daoVente.find(rs.getInt("id_vente")), rs.getDouble("montant"), rs.getString("paiement.date"),
                                rs.getString("status"), rs.getString("dateEffet"), rs.getString("type"));
                list.add(v);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
