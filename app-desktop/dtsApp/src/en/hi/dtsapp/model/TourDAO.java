/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package en.hi.dtsapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/** Class objectives (static methods, OOD unnecessary)
 * Fetches all tours from database
 * Creates Java Tour objects from these Tours
 * Puts the Java Tour objects into an Unmodifiable list
 * Returns the Unmodifiable list
 * 
 * @author Erling Óskar Kristjánsson eok4@hi.is
 * @date
 * Háskóli Íslands
 */
public class TourDAO implements DAO {
    
    public static List<Tour> initiateTourCatalog() throws Exception {
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
    
    
    public static void main() throws Exception {
        long t0 = System.nanoTime();
        List<Tour> immutableList = initiateTourCatalog();
        long t1 = System.nanoTime();
        System.out.println("Length of immutable List is " + immutableList.size());
        System.out.println("Fetching tours and creating immutable list took " + ((t1-t0)/Math.pow(10,9)) + " seconds");
        // for(int i = 0; i < 10; i++) System.out.println(immutableList.get(i).getName());
    }
}
