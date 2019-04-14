package en.hi.dtsapp.model.tours;

import en.hi.dtsapp.model.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/** Class objectives (static methods, OOD is unnecessary)
 * Fetches all tours from database
 * Creates Java Tour objects from these Tours
 * Puts the Java Tour objects into an Unmodifiable list
 * Returns the Unmodifiable list
 * 
 * @author Erling Óskar Kristjánsson eok4@hi.is
 */
public class TourDAO implements DAO {
    
    public static List<Tour> initiateTourCatalog(Connection conn)
            throws ParseException, SQLException {
        List<Tour> tourList = new ArrayList<>();
        Statement stmt = conn.createStatement();
            String s = "select * from tour";
            ResultSet rs = stmt.executeQuery(s);
            while (rs.next()) { // Make tours from each line. This is effectively the "createTour()" method
                Tour tour = tourFromRS(rs);
                tourList.add(tour);
            }
        List<Tour> immutableList = Collections.unmodifiableList(tourList);
        return immutableList; 
    }
    
    public static List<Tour> distinctTourCatalog(Connection conn) 
            throws ParseException, SQLException {
        List<Tour> tourList = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String s = "select distinct * from tour group by TourName";
        ResultSet rs = stmt.executeQuery(s);
        while (rs.next()) { // Make tours from each line. This is effectively the "createTour()" method
                Tour tour = tourFromRS(rs);
                tourList.add(tour);
        }
        List<Tour> immutableList = Collections.unmodifiableList(tourList);
//        System.out.println("length of distinct list is " + immutableList.size());
        return immutableList; 
    }
    
    public static List<Tour> initiateTourCatalog() 
            throws ParseException, SQLException, ClassNotFoundException {
        List<Tour> tourList = new ArrayList<>();
        try (Connection conn = getConnection()) {
            // Select all tours from tour table
            Statement stmt = conn.createStatement();
            String s = "select * from tour";
            ResultSet rs = stmt.executeQuery(s);
            while (rs.next()) { // Make tours from each line. This is effectively the "createTour()" method
                Tour tour = tourFromRS(rs);
                tourList.add(tour);
            }
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        List<Tour> immutableList = Collections.unmodifiableList(tourList);
        return immutableList; 
    }

    public static List<Tour> distinctTourCatalog() 
            throws ParseException, SQLException, ClassNotFoundException {
        List<Tour> tourList = new ArrayList<>();
        try (Connection conn = getConnection()) {
            // Select tours from tour table distinct by name
            Statement stmt = conn.createStatement();
            String s = "select distinct * from tour group by TourName";
            ResultSet rs = stmt.executeQuery(s);
            while (rs.next()) { // Make tours from each line. This is effectively the "createTour()" method
                Tour tour = tourFromRS(rs);
                tourList.add(tour);
            }
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        List<Tour> immutableList = Collections.unmodifiableList(tourList);
//        System.out.println("length of distinct list is " + immutableList.size());
        return immutableList; 
    }
    
    private static Tour tourFromRS(ResultSet rs)
            throws ParseException, SQLException {
        return new Tour(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12));
    }
    
     /**
     * @return conn, Connection to the DayTourSearch database
     * @throws ClassNotFoundException did not find JDBC driver class
     * @throws SQLException failed to establish a connection with database
     */
    public static Connection getConnection() 
            throws ClassNotFoundException, SQLException {
        try {
            Class.forName(DRIVER);
         }
        catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Failed to get class/driver in CustomerDAO.insertCustomer()");
            throw ex;
         }
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        return conn;
    }
    
    public static void main() throws Exception {
        long t0 = System.nanoTime();
        List<Tour> immutableList = initiateTourCatalog();
        long t1 = System.nanoTime();
        System.out.println("Length of immutable List is " + immutableList.size());
        System.out.println("Fetching tours and creating immutable list took " + ((t1-t0)/Math.pow(10,9)) + " seconds");
    }
}
