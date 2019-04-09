/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprint2;

import en.hi.dtsapp.model.Booking;
import en.hi.dtsapp.model.DAOs.BookingDAO;
import static en.hi.dtsapp.model.DAOs.BookingDAO.deleteBookings;
import static en.hi.dtsapp.model.DAOs.BookingDAO.insertBooking;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @authors
 * Erling Óskar Kristjánsson eok4@hi.is,
 * Andrea Ósk Sigurðardóttir aos26@hi.is
 * Davíð Freyr Björnsson dfb2@hi.is
 */
public class BookingDAOTest {
    
    private String validCustomerEmail,
                   validTourName, validTourOperator, 
                   validTourLocation, validTourStartTime, validTourDate,
                   validTravelers;
    
    private String invalidCustomerEmail,
                   invalidTourName, invalidTourOperator,
                   invalidTourLocation, invalidTourStartTime, invalidTourDate,
                   invalidTravelers;
    
    private BookingDAO bookingDAO;
    
    private Booking validBooking;
    private Booking invalidBooking;
    
    private CustomerPerson cp;
    
    @Before
    public void setUp() throws SQLException {    
        
        
        validCustomerEmail  = "dummyCustomer@hi.is";
        validTourName = "Artic Fox hunt";
        validTourOperator = "Isafjordur Tours";
        validTourLocation = "Isafjordur";
        validTourStartTime = "0900";
        validTourDate = "02062019";
        validTravelers = "20";
        
        
        
        invalidCustomerEmail  = "dummyCustomerhi;is";
        invalidTourName = ";"; 
        invalidTourOperator = ";";
        invalidTourLocation = ";";
        invalidTourStartTime = "090;";
        invalidTourDate = "1104201;";
        invalidTravelers = "20;";
        
        bookingDAO = new BookingDAO();
        deleteBookings(validCustomerEmail);
        deleteBookings(invalidCustomerEmail);
        
        
    }
    
    @After
    public void tearDown() {
        bookingDAO = null;
    }
    
   @Test
   public void insertValidBooking() throws SQLException {
       assertTrue(insertBooking(validCustomerEmail, validTourName,
               validTourOperator, validTourLocation, validTourStartTime,
               validTourDate, validTravelers) == 1);
   }
    
   @Test
   public void insertDuplicateBooking() throws SQLException {
               insertBooking(validCustomerEmail, validTourName,
               validTourOperator, validTourLocation, validTourStartTime,
               validTourDate, validTravelers);
       assertTrue(insertBooking(validCustomerEmail, validTourName,
               validTourOperator, validTourLocation, validTourStartTime,
               validTourDate, validTravelers) == 0);
   }
   
   @Test
   public void insertInvalidBooking() throws SQLException {
       assertTrue(insertBooking(invalidCustomerEmail, invalidTourName,
               invalidTourOperator, invalidTourLocation, invalidTourStartTime,
               invalidTourDate, invalidTravelers) == -2);
   }
   
   @Test
   public void insertInvalidEmail() throws SQLException {
       assertTrue(insertBooking(invalidCustomerEmail, validTourName,
               validTourOperator, validTourLocation, validTourStartTime,
               validTourDate, validTravelers) == -2); 
   }
   
   @Test
   public void insertInvalidTourName() throws SQLException {
       assertTrue(insertBooking(validCustomerEmail, invalidTourName,
               validTourOperator, validTourLocation, validTourStartTime,
               validTourDate, validTravelers) == -3); 
   }
   
   @Test
   public void insertInvalidTourOperator() throws SQLException {
       assertTrue(insertBooking(validCustomerEmail, validTourName,
               invalidTourOperator, validTourLocation, validTourStartTime,
               validTourDate, validTravelers) == -4); 
   }
   
   @Test
   public void insertInvalidTourLocation() throws SQLException {
       assertTrue(insertBooking(validCustomerEmail, validTourName,
               validTourOperator, invalidTourLocation, validTourStartTime,
               validTourDate, validTravelers) == -5); 
   }
   
   @Test
   public void insertInvalidTourStartTime() throws SQLException {
       assertTrue(insertBooking(validCustomerEmail, validTourName,
               validTourOperator, validTourLocation, invalidTourStartTime,
               validTourDate, validTravelers) == -6);
   }
   
   @Test
   public void insertInvalidTourDate() throws SQLException {
       assertTrue(insertBooking(validCustomerEmail, validTourName,
               validTourOperator, validTourLocation, validTourStartTime,
               invalidTourDate, validTravelers) == -7);
   }
   
   @Test
   public void insertInvalidTravelers() throws SQLException {
       assertTrue(insertBooking(validCustomerEmail, validTourName,
               validTourOperator, validTourLocation, validTourStartTime,
               validTourDate, invalidTravelers) == -8);
   }
}
