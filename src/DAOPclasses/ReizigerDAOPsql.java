package DAOPclasses;

import classes.Reiziger;
import interfaces.AdresDAO;
import interfaces.ReizigerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO{
    private Connection connection;
    private AdresDAO adao;
    private List<Reiziger> lijstReiziger = new ArrayList<>();

    public ReizigerDAOPsql(Connection conn){
        connection = conn;

    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            String query = "INSERT INTO reiziger(reiziger_id,voorletters,tussenvoegsel,achternaam,geboortedatum) VALUES (?,?,?,?,?)";

            //bevraag de resultaten
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, reiziger.getReiziger_id());
            ps.setString(2, reiziger.getVoorletters());
            ps.setString(3, reiziger.getTussenvoegsel());
            ps.setString(4, reiziger.getAchternaam());
            ps.setDate(5, (Date) reiziger.getGeboortedatum());
            ps.executeUpdate();
            return true;

        } catch (SQLException sqlex) {
            System.err.println("kan niet worden geplaatst " + sqlex.getMessage());
            return false;
        }
    }


    @Override
    public boolean update(Reiziger reiziger) {
        //"UPDATE reiziger SET ....."
        try {
            String query = "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ?  WHERE reiziger_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, reiziger.getVoorletters());
            ps.setString(2, reiziger.getTussenvoegsel());
            ps.setString(3, reiziger.getAchternaam());
            ps.setDate(4, (Date) reiziger.getGeboortedatum());
            ps.setInt(5, reiziger.getReiziger_id());
            ps.executeUpdate();
            ps.close();
            return true;

        } catch (SQLException sqlex) {
            System.err.println("update ging mis" + sqlex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        //"DELETE FROM reiziger WHERE  "
        try {
            String query = "DELETE FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, reiziger.getReiziger_id());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException sqlex) {
            System.err.println("kan niet worden verwijderd " + sqlex.getMessage());
            return false;
        }
    }


    @Override
    public Reiziger findById(int id) {
        try {
            String query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Reiziger newReiziger = new Reiziger((rs.getInt("reiziger_id")),
                        rs.getString("voorletters"),
                        rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"),
                        rs.getDate("geboortedatum"));
                return newReiziger;
            }else {
                System.err.println("reiziger met dit id kan niet worden gevonden");
                return null;
            }
        }catch (SQLException sqlex) {
            System.err.println("reiziger met dit id kan niet worden gevonden" + sqlex.getMessage());
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbDatum(String datum) {
        //"SELECT * FROM reiziger WHERE geboortedatum = datum"
        try {
            String query = "SELECT * FROM reiziger WHERE geboortedatum = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setDate(1, Date.valueOf(datum));
            ResultSet rs = ps.executeQuery();
            lijstReiziger.clear();
            while (rs.next()){
                Reiziger newReiziger = new Reiziger((rs.getInt("reiziger_id")),  rs.getString("voorletters"), rs.getString("tussenvoegsel"), rs.getString("achternaam"), rs.getDate("geboortedatum"));
                lijstReiziger.add(newReiziger);
            }
            return lijstReiziger;
        }catch (SQLException sqlex) {
            System.err.println("kan reizigers met deze datum niet vinden" + sqlex.getMessage());
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        try {
            String query = "SELECT * FROM reiziger";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            lijstReiziger.clear();
            while (rs.next()){
                Reiziger newReiziger = new Reiziger((rs.getInt("reiziger_id")),  rs.getString("voorletters"), rs.getString("tussenvoegsel"), rs.getString("achternaam"), rs.getDate("geboortedatum"));
                lijstReiziger.add(newReiziger);


            }
            return lijstReiziger;
        }catch (SQLException sqlex) {
            System.err.println("kan niet worden opgehaald" + sqlex.getMessage());
            return null;
        }


    }
}
