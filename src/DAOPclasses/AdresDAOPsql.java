package DAOPclasses;

import classes.Adres;
import classes.Reiziger;
import interfaces.AdresDAO;
import interfaces.ReizigerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private Connection connection;
    private ReizigerDAO rdao;
    private List<Adres> lijstAdres = new ArrayList<>();

    public AdresDAOPsql(Connection conn){
        connection = conn;
    }


    @Override
    public boolean save(Adres adres) {
        try {
            String query = "INSERT INTO adres(adres_id,postcode,huisnummer,straat,woonplaats,reiziger_id) VALUES (?,?,?,?,?,?)";

            //bevraag de resultaten
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, adres.getAdres_id());
            ps.setString(2, adres.getPostcode());
            ps.setString(3, adres.getHuisnummer());
            ps.setString(4, adres.getStraat());
            ps.setString(5, adres.getWoonplaats());
            ps.setInt(6, adres.getReiziger_id());
            ps.executeUpdate();
            return true;

        } catch (SQLException sqlex) {
            System.err.println("adres kan niet worden geplaatst " + sqlex.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try {
            String query = "UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ?  WHERE adres_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, adres.getPostcode());
            ps.setString(2, adres.getHuisnummer());
            ps.setString(3, adres.getStraat());
            ps.setString(4, adres.getWoonplaats());
            ps.setInt(5, adres.getReiziger_id());
            ps.setInt(6, adres.getAdres_id());
            ps.executeUpdate();
            ps.close();
            return true;

        } catch (SQLException sqlex) {
            System.err.println("update ging mis" + sqlex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            String query = "DELETE FROM adres WHERE adres_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, adres.getAdres_id());
            System.out.println(adres.getAdres_id());
            ps.executeUpdate();
            ps.close();
            return true;

        } catch (SQLException sqlex) {
            System.err.println("adres kan niet worden verwijderd " + sqlex.getMessage());
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            String query = "SELECT FROM adres WHERE reiziger_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, reiziger.getReiziger_id());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Adres((rs.getInt("adres_id")),  rs.getString("postcode"), rs.getString("huisnummer"), rs.getString("straat"), rs.getString("woonplaats"),rs.getInt("reiziger_id"));
            }
        } catch (SQLException sqlex) {
            System.err.println("adres kan niet worden verwijderd " + sqlex.getMessage());
        }
        return null;
    }



    @Override
    public List<Adres> findAll() {
        try {
            String query = "SELECT * FROM adres";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            lijstAdres.clear();
            while (rs.next()){
                Adres newAdres = new Adres((rs.getInt("adres_id")),  rs.getString("postcode"), rs.getString("huisnummer"), rs.getString("straat"), rs.getString("woonplaats"),rs.getInt("reiziger_id"));
                lijstAdres.add(newAdres);
            }
            return lijstAdres;
        }catch (SQLException sqlex) {
            System.err.println("kan niet worden opgehaald" + sqlex.getMessage());
            return null;
        }
    }
}
