/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprint1;

import en.hi.dtsapp.controller.TourCatalog;
import java.text.ParseException;
import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Andrea Ósk Sigurðardóttir - aos26@hi.is
 */
public class SearchDateTest {
     private TourCatalog tourCatalog;
    private String validDate1From, validDate1To, invalidDate1From, invalidDate1To;
    private String validDate2From, validDate2To, invalidDate2From, invalidDate2To;
    
    @Before
    public void setUp() throws Exception {
        tourCatalog = new TourCatalog();
        validDate1From = "01042019";
        validDate1To = "05042019";
        invalidDate1From = "01242019";
        invalidDate1To = "02242019";
        validDate2From = "05052019";
        validDate2To = "07052019";
        invalidDate2From = "010411290";
        invalidDate2To = "050411290";
    }
    
    @After
    public void tearDown() {
        tourCatalog = null;
    }
    
    @Test
    public void testNotNull() {
        assertNotNull(tourCatalog.getFullTourList());
    }
    
    @Test
    public void testNotEmpty() {
        assertFalse(tourCatalog.getFullTourList().isEmpty());
    }
    
    @Test
    public void testSearchByValidDate() throws ParseException {
        assertFalse(tourCatalog.getToursByDate(validDate2From, validDate2To).isEmpty());
    }
    
    @Test
    public void testSearchByInvalidDate() throws ParseException {
        assertTrue(tourCatalog.getToursByDate(invalidDate2From, invalidDate2To).isEmpty());
    }
     
}
