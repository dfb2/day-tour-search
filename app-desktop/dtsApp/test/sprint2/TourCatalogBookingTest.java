/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprint2;

import en.hi.dtsapp.controller.TourCatalog;
import en.hi.dtsapp.model.tours.Tour;
import java.sql.SQLException;
import java.text.ParseException;
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
public class TourCatalogBookingTest {
    
    private TourCatalog tourCatalog;
    private Tour tour;
    
    private int validTravelers;
    
    private int invalidTravelers;
            
    @Before
    public void setUp() 
            throws SQLException, ClassNotFoundException, ParseException {    
        tourCatalog = new TourCatalog();
        
        tourCatalog.setCustomerPerson("John", "johnspw", "john666@hi.is");
        System.out.println(tourCatalog.getFullTourList().size());
        int index = (int)Math.round(Math.random()*tourCatalog.getFullTourList().size());
        System.out.println(index);
        tour = tourCatalog.getFullTourList().get(index);
        
        validTravelers = 2;
        invalidTravelers = -1;
        
    }
    
    @After
    public void tearDown() {
        tourCatalog = null;
        tour = null;
    }
    
   @Test
   public void insertValidBooking() throws SQLException, 
           IllegalArgumentException, NullPointerException, ClassNotFoundException {
       assertTrue(tourCatalog.bookTour(tour, validTravelers));
   }
    
   @Test(expected = IllegalArgumentException.class)
   public void insertDuplicateBooking() throws SQLException, 
           IllegalArgumentException, NullPointerException, ClassNotFoundException {
       assertFalse(tourCatalog.bookTour(tour, validTravelers)
       && tourCatalog.bookTour(tour, validTravelers));

   }
   
   @Test(expected = IllegalArgumentException.class)
   public void insertInvalidBooking() throws SQLException, 
           IllegalArgumentException, NullPointerException, ClassNotFoundException {
       assertFalse(tourCatalog.bookTour(tour, invalidTravelers));

   }
   
   @Test
   public void insertInvalidEmail() throws 
           SQLException, NullPointerException, ClassNotFoundException {
        tourCatalog.setCustomerPerson("John2", "johnspw", "john666@hi.is");
   }
   /*
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
*/
}
