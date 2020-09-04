import DAOPclasses.AdresDAOPsql;
import DAOPclasses.ReizigerDAOPsql;
import classes.Adres;
import classes.Reiziger;
import interfaces.AdresDAO;
import interfaces.ReizigerDAO;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=titatovenaar";

        try {
            Connection conn = DriverManager.getConnection(url);

            ReizigerDAO reizigerDAO = new ReizigerDAOPsql(conn);
            testReizigerDAO(reizigerDAO);
            AdresDAO adresDAO = new AdresDAOPsql(conn);
            testAdresDAO(adresDAO);

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
        Reiziger sietske = new Reiziger(79, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");


        // Maak een update aan een reiziger en persisteer deze in de database
        Reiziger gerrit = new Reiziger(2, "G", "van", "Rijn", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] eerst is reiziger met id 2:\n");
        Reiziger reizigerUpdate = rdao.findById(2);
        System.out.println(reizigerUpdate);
        System.out.print("[Test] na ReizigerDAO.update(gerrit):\n");
        rdao.update(gerrit);
        Reiziger reizigerNaUpdate = rdao.findById(2);
        System.out.println(reizigerNaUpdate);



        // definieer een reiziger aan en verwijder deze uit de database
        String gbdatumSietske = "1981-03-14";
        Reiziger sietskeVerwijder = new Reiziger(78, "S", "", "Boers", java.sql.Date.valueOf(gbdatumSietske));
        System.out.print("\n[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(sietskeVerwijder);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");


        // definieer een id en vind de reiziger met dit id in de database
        int vindById = 4;
        System.out.println("[Test] ReizigerDAO.findById(4) geeft de reiziger:");
        Reiziger reiziger = rdao.findById(vindById);
        System.out.println(reiziger);


        // definieer een geboortedatum en vind de reiziger met deze geboortedatum in de database
        String vindByGD = "1998-08-11";
        List<Reiziger> reizigersGeb = rdao.findByGbDatum(vindByGD);
        System.out.println("\n[Test] ReizigerDAO.findByGbDatum() geeft de volgende reizigers:");
        for (Reiziger r : reizigersGeb) {
            System.out.println(r);
        }
    }
    private static void testAdresDAO(AdresDAO adao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle reizigers op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adresen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // Maak een nieuw adres aan en persisteer deze in de database
        Adres huis1 = new Adres(77, "1234AB", "26", "Pietterstraat","Hilversum",1);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adao.save(huis1);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");

        // Maak een update aan een adres en persisteer deze in de database
        Adres adres1 = new Adres(65, "473DF", "69", "Heidenstraat","Rijkenlaan",1);
        System.out.print("[Test] eerst is adres van Sietske:\n");
        //gebruik voor test even een reiziger
        String gbdatum = "1981-03-14";
        Adres adresUpdate = adao.findByReiziger(new Reiziger(78, "S", "", "Boers", java.sql.Date.valueOf(gbdatum)));
        System.out.println(adresUpdate);
        System.out.print("[Test] na AdresDAO.update():\n");
        Adres adresUpdate2 = adao.findByReiziger(new Reiziger(78, "S", "", "Boers", java.sql.Date.valueOf(gbdatum)));
        System.out.println(adresUpdate2);
        adao.update(adres1);


        // definieer een adres aan en verwijder deze uit de database
        Adres adresVerwijder = new Adres(5, "3456FF", "34", "Geestwijk", "Heerlen",4);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na adressenDAO.delete() ");
        adao.delete(adresVerwijder);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");

    }
}
