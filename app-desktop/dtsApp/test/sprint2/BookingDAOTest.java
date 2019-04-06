/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprint2;

import en.hi.dtsapp.model.DAOs.BookingDAO;
import static en.hi.dtsapp.model.DAOs.BookingDAO.insertBooking;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nafn: Davíð Freyr Björnsson, Háskóli Íslands, netfang: dfb2@hi.is
 */
public class BookingDAOTest {
    
    private String validCustomerEmail1, validCustomerEmail2,
                   validCustomerEmail3, validCustomerEmail4,
                   validCustomerEmail5, validCustomerEmail6,
                   validCustomerEmail7,
                   validTourName, validTourOperator, 
                   validTourLocation, validTourStartTime, validTourDate,
                   validTravelers, validTourNameNew; 
    
    private String invalidCustomerEmail1, invalidCustomerEmail2,
                   invalidTourName, invalidTourOperator,
                   invalidTourLocation, invalidTourStartTime, invalidTourDate,
                   invalidTravelers;
    
    private BookingDAO bookingDAO;
    
    @Before
    public void setUp() {    
            
        
        validCustomerEmail1  = "dummyCustomer1@hi.is";
        validCustomerEmail2  = "dummyCustomer2@hi.is";
        validCustomerEmail3  = "dummyCustomer3@hi.is";
        validCustomerEmail4  = "dummyCustomer4@hi.is";
        validCustomerEmail5  = "dummyCustomer5@hi.is";
        validCustomerEmail6  = "dummyCustomer6@hi.is";
        validCustomerEmail7  = "dummyCustomer7@hi.is";
        validTourName = "Artic Fox hunt";
        validTourNameNew = validTourName + " test";
        
        validTourOperator = "Isafjordur Tours";
        validTourLocation = "Isafjordur";
        validTourStartTime = "0900";
        validTourDate = "02062019";
        validTravelers = "20";     
        
        invalidCustomerEmail1  = "dummyCustomer1hi.is";
        invalidCustomerEmail2  = "dummyCustomer2hi.is";
        invalidTourName = ";"; 
        invalidTourOperator = ";";
        invalidTourLocation = ";";
        invalidTourStartTime = "090.";
        invalidTourDate = "1104201.";
        invalidTravelers = "20;";
        
        bookingDAO = new BookingDAO();
        
    }
    
    @After
    public void tearDown() {
        bookingDAO = null;
    }
    
   @Test
   public void insertValidBooking() throws SQLException {
       assertTrue(insertBooking(validCustomerEmail1, validTourNameNew,
               validTourOperator, validTourLocation, validTourStartTime,
               validTourDate, validTravelers) == 1);
   }
    
   @Test
   public void insertDuplicateBooking() throws SQLException {
       assertTrue(insertBooking(validCustomerEmail1, validTourNameNew,
               validTourOperator, validTourLocation, validTourStartTime,
               validTourDate, validTravelers) == 0);
   }
   
   @Test
   public void insertInvalidBooking() throws SQLException {
       assertTrue(insertBooking(invalidCustomerEmail1, invalidTourName,
               invalidTourOperator, invalidTourLocation, invalidTourStartTime,
               invalidTourDate, invalidTravelers) == -2);
   }
   
   @Test
   public void insertInvalidEmail() throws SQLException {
       assertTrue(insertBooking(invalidCustomerEmail2, validTourName,
               validTourOperator, validTourLocation, validTourStartTime,
               validTourDate, validTravelers) == -2); 
   }
   
   @Test
   public void insertInvalidTourName() throws SQLException {
       assertTrue(insertBooking(validCustomerEmail2, invalidTourName,
               validTourOperator, validTourLocation, validTourStartTime,
               validTourDate, validTravelers) == -3); 
   }
   
   @Test
   public void insertInvalidTourOperator() throws SQLException {
       assertTrue(insertBooking(validCustomerEmail3, validTourName,
               invalidTourOperator, validTourLocation, validTourStartTime,
               validTourDate, validTravelers) == -4); 
   }
   
   @Test
   public void insertInvalidTourLocation() throws SQLException {
       assertTrue(insertBooking(validCustomerEmail4, validTourName,
               validTourOperator, invalidTourLocation, validTourStartTime,
               validTourDate, validTravelers) == -5); 
   }
   
   @Test
   public void insertInvalidTourStartTime() throws SQLException {
       assertTrue(insertBooking(validCustomerEmail5, validTourName,
               validTourOperator, validTourLocation, invalidTourStartTime,
               validTourDate, validTravelers) == -6); 
   }
   
   @Test
   public void insertInvalidTourDate() throws SQLException {
       assertTrue(insertBooking(validCustomerEmail6, validTourName,
               validTourOperator, validTourLocation, validTourStartTime,
               invalidTourDate, validTravelers) == -7);
   }
   
   @Test
   public void insertInvalidTravelers() throws SQLException {
       assertTrue(insertBooking(validCustomerEmail7, validTourName,
               validTourOperator, validTourLocation, validTourStartTime,
               validTourDate, invalidTravelers) == -8);
   }
}
