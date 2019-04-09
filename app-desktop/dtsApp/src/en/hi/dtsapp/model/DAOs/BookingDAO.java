package en.hi.dtsapp.model.DAOs;

import en.hi.dtsapp.model.Booking;
import static en.hi.dtsapp.model.DAOs.DAO.DB_URL;
import static en.hi.dtsapp.model.DAOs.DAO.PASS;
import static en.hi.dtsapp.model.DAOs.DAO.USER;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

/**
 * The primary method a user would be interested in is at the top:
 *      public static int insertBooking(..)
 * 
 * The main method of this class deletes all bookings made by "dummyCustomer@hi.is"
 * Can be called statically:
 *      BookingDAOjava.main();
 * 
 * @author Erling Oskar Kristjansson, eok4@hi.is
 */
public class BookingDAO implements DAO { 
    
    /**
     * @param booking to be added to the Booking table in the Day Tour Database
     * @return  1 if value was inserted
     *          0 if customer already booked this exact tour
     *         -1 if failed to connect to database
     *         -9 if the tour is fully booked
     *         -404 other unexplainable SQLException. Error message printed.
     * @throws SQLException  
     */
    public static int insertBooking(Booking booking)  throws SQLException{
        
        String customerEmail = booking.getCpEmail();
        String tourName = booking.getTourName();
        String tourOperator = booking.getTourOperator();
        String tourLocation = booking.getTourLocation();
        String tourStartTime = booking.getStartTimeAsString();
        String tourDate = booking.getDateAsString();
        int travelers = booking.getTravelers();

        System.out.println("BookingDAO: " + customerEmail);
        try {
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Failed to connect to driver in CustomerDAO.insertCustomer()");
            return -1;
        }
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Connected to database using BookingDAO.insertBooking()");

         // Check if booking already in table
        String s = "select * from booking where CustomerEmail = ?"
                 + " and TourName = ?" 
                 + " and TourOperator = ?"
                 + " and TourLocation = ?"
                 + " and TourStartTime = ?"
                 + " and TourDate = ?;";
        PreparedStatement pstmt = conn.prepareStatement(s);
        pstmt.setString(1, customerEmail);
        pstmt.setString(2, tourName);
        pstmt.setString(3, tourOperator);
        pstmt.setString(4, tourLocation);
        pstmt.setString(5, tourStartTime);
        pstmt.setString(6, tourDate);
        
        try{ // Will work unless table has been dropped or something...
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                if(rs.getString(1).equals(customerEmail)
                   && rs.getString(2).equals(tourName)
                   && rs.getString(3).equals(tourOperator)
                   && rs.getString(4).equals(tourLocation)
                   && rs.getString(5).equals(tourStartTime)
                   && rs.getString(6).equals(tourDate)) return 0;
            }
        } catch (SQLException e) {
            System.err.println("Failed to select from booking table in BookingDAO.insertBooking(..)");
            System.err.println(e.getMessage());
            return -404;
        }
       
        if( !updateTourTravelers(conn, travelers, tourName, tourOperator, tourLocation, 
            tourStartTime, tourDate, customerEmail)) return -9; // Tour is fully booked, we stop here.

        s = "insert into booking values(?,?,?,?,?,?,?);";
        pstmt.clearParameters();
        pstmt = conn.prepareStatement(s);
        pstmt.setString(1, customerEmail);
        pstmt.setString(2, tourName);
        pstmt.setString(3, tourOperator);
        pstmt.setString(4, tourLocation);
        pstmt.setString(5, tourStartTime);
        pstmt.setString(6, tourDate);
        pstmt.setInt(7, travelers);
        try{
            pstmt.executeUpdate();        
        } catch (SQLException e) {
            System.err.println("Failed to insert value into booking table in BookingDAO.insertBooking(..)");
            System.err.println(e.getMessage());
            return -404;
        }
        
        
        return 1;
    }

    
    private static boolean updateTourTravelers(Connection conn, int travelers,
        String tourName, String tourOperator, String tourLocation, String tourStartTime,
        String tourDate, String customerEmail) throws SQLException{
        String s = "update tour set TourTravellers = TourTravellers + " + travelers
                + " where TourName = ?"
                + " and TourOperator = ?"
                + " and TourLocation = ?"
                + " and TourStartTime = ?"
                + " and tourDate = ?;";
        PreparedStatement pstmt = conn.prepareStatement(s);
        pstmt.setString(1, tourName);
        pstmt.setString(2, tourOperator);
        pstmt.setString(3, tourLocation);
        pstmt.setString(4, tourStartTime);
        pstmt.setString(5, tourDate);
        
        try{
            System.out.println(s);
            pstmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e){
            System.err.println(e.getMessage());
            System.err.println(
                "Tried to update travellers in Tour table but it seems to be full".concat(
                customerEmail).concat(", in BookingDAO.insertBooking()"));
            return false;
        }
        return true;
    }

    
    // Delete all bookings from Booking table that match customerEmail   
    // Obviously not for distribution purposes...
    public static boolean deleteBookings(String customerEmail) throws SQLException{
        if(DTSMethods.isBadInput(customerEmail)) { System.err.println("bad email in BookingDAO.insertBooking()"); return false; }
        customerEmail = customerEmail.trim();
        try {
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Failed to connect to driver in CustomerDAO.insertCustomer()");
            return false;
        }
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        // Make statement and delete booking
        Statement stmt = conn.createStatement(); // could've used a prepared statement
        String s = "delete from booking where customerEmail='" + customerEmail + "';";
        try{
            System.out.println(s);
            stmt.executeUpdate(s);
        } catch(SQLException e){
            System.err.println(e.getMessage());
            System.err.println("Failed to delete bookings for customer with email "
            + customerEmail + " in BookingDAO.deleteBooking(). No action taken.");
            return false;
        }
        return true;
    }
    
    
    // Test methods. Also deletes all bookings by "dummyCustomer@hi.is"
    public static void main(String[] args) throws SQLException {
        deleteBookings("dummyCustomer@hi.is");
        long t0 = System.nanoTime();
        long t1 = System.nanoTime();
        System.out.println("Inserting Dummy Booking took " + ((t1-t0)/Math.pow(10,9)) + " seconds");
        deleteBookings("dummyCustomer@hi.is");
    }
}