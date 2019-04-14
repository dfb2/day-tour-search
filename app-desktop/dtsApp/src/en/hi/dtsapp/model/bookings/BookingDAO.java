package en.hi.dtsapp.model.bookings;

import en.hi.dtsapp.model.DAO;
import static en.hi.dtsapp.model.DAO.DB_URL;
import static en.hi.dtsapp.model.DAO.PASS;
import static en.hi.dtsapp.model.DAO.USER;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

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
     * @throws java.lang.ClassNotFoundException failed to get JDBC Driver
     * @throws SQLException failed to connect to database or perform DB action
     * @throws IllegalArgumentException if Customer already booked this tour
     */
    protected static boolean insertBooking(Booking booking)
            throws ClassNotFoundException, SQLException, IllegalArgumentException {
        try (Connection conn = getConnection()) {
            if(bookingInTable(conn, booking)){
                throw new IllegalArgumentException("Customer already booked this tour.");
            }
            if(updateTourTravelers(conn, booking)) {
                insertBooking(conn, booking);
                conn.close();
                return true;
            }
            conn.close();
            return false;
        }
    }
    
    private static void insertBooking(
            Connection conn, Booking booking)
            throws ClassNotFoundException, SQLException{
        String s = "insert into booking values(?,?,?,?,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(s);
        pstmt.clearParameters();
        pstmt = conn.prepareStatement(s);
        pstmt.setString(1, booking.getCpEmail());
        pstmt.setString(2, booking.getTourName());
        pstmt.setString(3, booking.getTourOperator());
        pstmt.setString(4, booking.getTourLocation());
        pstmt.setString(5, booking.getStartTimeAsString());
        pstmt.setString(6, booking.getDateAsString());
        pstmt.setInt(7, booking.getTravelers());
        pstmt.executeUpdate();
    }
    
    private static boolean bookingInTable(
            Connection conn, Booking booking)
            throws SQLException {
        ResultSet rs = selectBooking(conn, booking);
        while(rs.next()){
                if(rs.getString(1).equals(booking.getCpEmail())
                   && rs.getString(2).equals(booking.getTourName())
                   && rs.getString(3).equals(booking.getTourOperator())
                   && rs.getString(4).equals(booking.getTourLocation())
                   && rs.getString(5).equals(booking.getStartTimeAsString())
                   && rs.getString(6).equals(booking.getDateAsString())) return true;
        }
        return false;
    }

    private static ResultSet selectBooking(
            Connection conn, Booking booking)
            throws SQLException { 
        // Check if booking already in table
        String s = "select * from booking where CustomerEmail = ?"
                 + " and TourName = ?" 
                 + " and TourOperator = ?"
                 + " and TourLocation = ?"
                 + " and TourStartTime = ?"
                 + " and TourDate = ?;";
        PreparedStatement pstmt = conn.prepareStatement(s);
        pstmt.setString(1, booking.getCpEmail());
        pstmt.setString(2, booking.getTourName());
        pstmt.setString(3, booking.getTourOperator());
        pstmt.setString(4, booking.getTourLocation());
        pstmt.setString(5, booking.getStartTimeAsString());
        pstmt.setString(6, booking.getDateAsString());
        return pstmt.executeQuery();
    }
    
    private static boolean updateTourTravelers(
            Connection conn, Booking booking) throws SQLException{
        String s = "update tour set TourTravellers = TourTravellers + "
                + booking.getTravelers()
                + " where TourName = ?"
                + " and TourOperator = ?"
                + " and TourLocation = ?"
                + " and TourStartTime = ?"
                + " and tourDate = ?;";
        PreparedStatement pstmt = conn.prepareStatement(s);
        pstmt.setString(1, booking.getTourName());
        pstmt.setString(2, booking.getTourOperator());
        pstmt.setString(3, booking.getTourLocation());
        pstmt.setString(4, booking.getStartTimeAsString());
        pstmt.setString(5, booking.getDateAsString());
        
        try{
            System.out.println(s);
            pstmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e){
            System.err.println(e.getMessage());
            System.err.println(
                "Tried to update travellers in Tour table but it seems to be full".concat(
                booking.getCpEmail()).concat(", in BookingDAO.insertBooking()"));
            return false;
        }
        return true;
    }

    
    /*
    // Delete all bookings from Booking table that match customerEmail   
    // Obviously not for distribution purposes...
    public static boolean deleteBookings(String customerEmail) throws SQLException{
        if(!DBO.validateEmail(customerEmail)) {
            System.err.println("bad email in BookingDAO.insertBooking()"); return false; }
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
    
     /**
     * @return conn, Connection to the DayTourSearch database
     * @throws ClassNotFoundException did not find JDBC driver class
     * @throws SQLException failed to establish a connection with database
     */
    private static Connection getConnection() throws ClassNotFoundException, SQLException {
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
    
    // Test methods. Also deletes all bookings by "dummyCustomer@hi.is"
    public static void main(String[] args) throws SQLException {
    //    deleteBookings("dummyCustomer@hi.is");
        long t0 = System.nanoTime();
        long t1 = System.nanoTime();
        System.out.println("Inserting Dummy Booking took " + ((t1-t0)/Math.pow(10,9)) + " seconds");
    //    deleteBookings("dummyCustomer@hi.is");
    }
}