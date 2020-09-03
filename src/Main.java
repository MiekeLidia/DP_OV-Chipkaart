import DAOPclasses.ReizigerDAOPsql;
import classes.Reiziger;
import interfaces.ReizigerDAO;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=titatovenaar";

        try {
            Connection conn = DriverManager.getConnection(url);

            //voer een query uit

            String query = "SELECT * FROM reiziger";

            //bevraag de resultaten
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

//            System.out.println("alle reizigers:");
//            var i = 1;
//            while (rs.next()){
//                System.out.println("#" + i + ": " + rs.getString("voorletters") + " "+ rs.getString("tussenvoegsel") + " "+ rs.getString("achternaam") + " (" + rs.getString("geboortedatum") + ")");
//                i ++;
//            }

            ReizigerDAO reizigerDAO = new ReizigerDAOPsql(conn);
            testReizigerDAO(reizigerDAO);

        } catch (SQLException sqlex) {
            System.err.println("kan niet worden opgehaald" + sqlex.getMessage());
        }


    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database

        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(75, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Maak een update aan een reiziger en persisteer deze in de database

        String gbdatumUpdate = "2002-09-17";
        Reiziger gerrit = new Reiziger(1, "G", "van", "Rijn", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] ReizigerDAO.update(gerrit)");
        rdao.update(gerrit);


        // definieer een reiziger aan en verwijder deze uit de database

        String gbdatumSietske = "1981-03-14";
        Reiziger sietskeVerwijder = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatumSietske));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(sietskeVerwijder);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // definieer een id en vind de reiziger met dit id in de database

        int vindById = 4;
        System.out.println("[Test] ReizigerDAO.findById() geeft de reiziger:\n");
        rdao.findById(vindById);

        // definieer een id en vind de reiziger met dit id in de database

        String vindByGD = "1998-08-11";
        List<Reiziger> reizigersGeb = rdao.findByGbDatum(vindByGD);
        System.out.println("[Test] ReizigerDAO.findByGbDatum() geeft de volgende reizigers:");
        for (Reiziger r : reizigersGeb) {
            System.out.println(r);
        }




    }
}
