package en.hi.dtsapp.model.DAOs;

import static en.hi.dtsapp.model.DAOs.CustomerDAO.insertCustomer;
import static en.hi.dtsapp.model.DAOs.DAO.DB_URL;
import static en.hi.dtsapp.model.DAOs.DAO.PASS;
import static en.hi.dtsapp.model.DAOs.DAO.USER;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

/**
 *
 * @author ellio
 */
public class BookingDAO implements DAO { 
    
    // Delete all bookings from Booking table that match customerEmail   
    public static boolean deleteBookings(String customerEmail) throws SQLException{
        if(DTSMethods.isBadInput(customerEmail)) { System.out.println("bad input in BookingDAO.insertBooking()"); return false; }
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
     * Adds a new Booking to Booking Table
     * @return  1 if value was inserted
     *          0 if customer already booked this exact tour
     *         -1 if failed to connect to database
     *         -2,...,-8 if bad input contains dubious characters
     *         -9 if a booking was made but it was actually full... 
     *              because we must either make the booking first or
     *                increase the value of the TourTravellers in Tour Table first
     *              do we want to deal with that?
     *              Might actually be easiest to do with a constraint in the DB
     * @throws SQLException 
     */
    public static int insertBooking(String customerEmail, String tourName,
            String tourOperator, String tourLocation, String tourStartTime, 
            String tourDate, String travelers)  throws SQLException{
        if(DTSMethods.isBadInput(customerEmail)) { System.out.println("bad input in BookingDAO.insertBooking()"); return -4; }
        if(DTSMethods.isBadInput(tourName)) { System.out.println("bad input in BookingDAO.insertBooking()"); return -4; }
        if(DTSMethods.isBadInput(tourOperator)) { System.out.println("bad input in BookingDAO.insertBooking()"); return -4; }
        if(DTSMethods.isBadInput(tourLocation)) { System.out.println("bad input in BookingDAO.insertBooking()"); return -4; }
        if(DTSMethods.isBadInput(tourStartTime)) { System.out.println("bad input in BookingDAO.insertBooking()"); return -4; }
        if(DTSMethods.isBadInput(tourDate)) { System.out.println("bad input in BookingDAO.insertBooking()"); return -4; }
        if(DTSMethods.isBadInput(travelers)) { System.out.println("bad input in BookingDAO.insertBooking()"); return -4; }

        customerEmail = customerEmail.trim(); // should use an array... lol
        tourName = tourName.trim();
        tourOperator = tourOperator.trim();
        tourLocation = tourLocation.trim();
        tourStartTime = tourStartTime.trim();
        tourDate = tourDate.trim();
        travelers = travelers.trim();

        try {
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Failed to connect to driver in CustomerDAO.insertCustomer()");
            return -1;
        }
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

        // Make statement and insert booking
        Statement stmt = conn.createStatement(); // could've used a prepared statement
        StringBuilder sb = new StringBuilder();
        sb = sb.append("insert into booking values('");
        sb = sb.append(customerEmail);
        sb = sb.append("', '");
        sb = sb.append(tourName);
        sb = sb.append("', '");
        sb = sb.append(tourOperator);
        sb = sb.append("', '");
        sb = sb.append(tourLocation);
        sb = sb.append("', '");
        sb = sb.append(tourStartTime);
        sb = sb.append("', '");
        sb = sb.append(tourDate);
        sb = sb.append("', ");
        sb = sb.append(travelers);
        sb = sb.append(");");
        String s = sb.toString();
        try{
            System.out.println(s);
            stmt.executeUpdate(s);
        } catch (SQLIntegrityConstraintViolationException e){
            System.err.println(e.getMessage());
            System.err.println(
                "Tried to insert duplicate booking value with email ".concat(
                customerEmail).concat(", in BookingDAO.insertBooking()"));
            return 0;
        } catch (SQLSyntaxErrorException se){
            System.err.println(se.getMessage());
            System.err.println(
                "Issue with syntax/value for customer with email ".concat(
                customerEmail).concat(", in BookingDAO.insertBooking()").concat(
                ". Probably caused by wrong date."));
            return 0;
        }
        // update TourTravellers in Tour table accordingly
        int i = Integer.parseInt(travelers);
        sb = new StringBuilder();
        sb = sb.append("update tour set TourTravellers = TourTravellers + " + i + "  where ");
        sb = sb.append("TourName = '");
        sb = sb.append(tourName);
        sb = sb.append("'and TourOperator= '");
        sb = sb.append(tourOperator);
        sb = sb.append("'and TourLocation= '");
        sb = sb.append(tourLocation);
        sb = sb.append("'and TourStartTime= '");
        sb = sb.append(tourStartTime);
        sb = sb.append("'and TourDate= '");
        sb = sb.append(tourDate);
        sb = sb.append("';");
        s = sb.toString();
        try{
            System.out.println(s);
            stmt.executeUpdate(s);
        } catch (SQLIntegrityConstraintViolationException e){
            System.err.println(e.getMessage());
            System.err.println(
                "Tried to update travellers in Tour table but it was full oh man oh man".concat(
                customerEmail).concat(", in BookingDAO.insertBooking()"));
            return -9;
        }
        
        return 1;
    }
    
    
    // Test methods
    public static void main(String[] args) throws SQLException {
        deleteBookings("dummyCustomer@hi.is");
        long t0 = System.nanoTime();
        System.out.println("insertBooking return  value: " + 
                insertBooking("dummyCustomer@hi.is", "Arctic Fox hunt",
                "Isafjordur Tours", "Isafjordur", "0900", "02062019","20"));
        long t1 = System.nanoTime();
        System.out.println("Inserting Dummy Booking took " + ((t1-t0)/Math.pow(10,9)) + " seconds");
        
    }
}
/**
 * String customerEmail, String tourName,
            String tourOperator, String tourLocation, String tourStartTime, 
            String tourDate, String travelers
 * 
 * 
 * CREATE TABLE BOOKING                        -- Spurning hvort thurfi primary key
    ( CustomerEmail varchar(20) references CUSTOMER(CustomerUserID)
    , TourName varchar(30) references Tour(TourName)
    , TourOperator varchar(30) references Tour(TourOperator)
    , TourLocation varchar(20) references Tour(TourLocation)
    , TourStartTime char(4) references Tour(TourStartTime)
    , TourDate char(8) references Tour(TourDate)
    , Travellers int not null
    , constraint BookingID primary key(CustomerEmail, TourName, 
      TourOperator, TourLocation, TourStartTime, TourDate) -- Hver viðskiptavinur getur þá bara átt eina virka bókun í hverjum tour
    );
 */