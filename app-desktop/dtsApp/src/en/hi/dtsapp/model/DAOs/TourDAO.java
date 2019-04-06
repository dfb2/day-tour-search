package en.hi.dtsapp.model.DAOs;

import en.hi.dtsapp.model.Tour;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
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
    
    public static List<Tour> initiateTourCatalog() throws Exception {
        List<Tour> tourList = new ArrayList<>();
        try {
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Failed to connect to driver in TourDAO.java");
        }
        try  {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to database using TourDAO.java");
            
            // Select all tours from tour table
            Statement stmt = conn.createStatement();
            String s = "select * from tour";
            ResultSet rs = stmt.executeQuery(s);
            while (rs.next()) { // Make tours from each line. This is effectively the "createTour()" method
                Tour tour = new Tour(
                        rs.getString(1),
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
                tourList.add(tour);
            }
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        List<Tour> immutableList = Collections.unmodifiableList(tourList);
        return immutableList; 
    }

    public static List<Tour> distinctTourCatalog() throws Exception {
        List<Tour> tourList = new ArrayList<>();
        try {
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Failed to connect ot driver in TourDAO.java");
        }
        try  {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to database using TourDAO.java");
            
            // Select tours from tour table distinct by name
            Statement stmt = conn.createStatement();
            String s = "select distinct * from tour group by TourName";
            ResultSet rs = stmt.executeQuery(s);
            while (rs.next()) { // Make tours from each line. This is effectively the "createTour()" method
                Tour tour = new Tour(
                        rs.getString(1),
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
                tourList.add(tour);
            }
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        List<Tour> immutableList = Collections.unmodifiableList(tourList);
        System.out.println("length of distinct list is " + immutableList.size());
        return immutableList; 
    }
    
    
    public static void main() throws Exception {
        long t0 = System.nanoTime();
        List<Tour> immutableList = initiateTourCatalog();
        long t1 = System.nanoTime();
        System.out.println("Length of immutable List is " + immutableList.size());
        System.out.println("Fetching tours and creating immutable list took " + ((t1-t0)/Math.pow(10,9)) + " seconds");
    }
}
