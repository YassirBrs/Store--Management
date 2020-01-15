package Gestion_Compte;

import Database.DataConnection;
import Gestion_Client.ClientDAOIMPL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompteDAOIMPL implements CompteDAO {
    private DataConnection dc;

    PreparedStatement pstm;
    ClientDAOIMPL clientDAOIMPL=new ClientDAOIMPL();

    public CompteDAOIMPL() {
        dc = DataConnection.getConnection();
    }

    @Override
    public Compte find(int id) {
        try {
            String url = "SELECT * FROM compte WHERE idclient=?";
            pstm = dc.conn.prepareStatement(url);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next() == false) {
                return null;
            } else {
                Compte c;
                do {
                    c = new Compte( rs.getInt("id"),clientDAOIMPL.find(id), rs.getDouble("solde"));
                } while (rs.next());
                return c;
            }
        } catch (Exception eee) {
            eee.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Compte c,double newSolde) {
        try{
            String query = "UPDATE compte SET solde=? WHERE id=?";
            pstm = dc.conn.prepareStatement(query);
            pstm.setDouble(1, newSolde);
            pstm.setInt(2, c.getClient().getId());
            pstm.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }

    }
}
