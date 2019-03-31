package sprint1;

import en.hi.dtsapp.controller.TourCatalog;
import java.text.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test Method for the class TourCatalog
 * @authors
 * Erling Óskar Kristjánsson eok4@hi.is,
 * Andrea Ósk Sigurðardóttir aos26@hi.is
 * Davíð Freyr Björnsson dfb2@hi.is
 */
public class TourCatalogTest {
    
    private TourCatalog tourCatalog;
    private String validKeyword1, invalidKeyword1;
    private String validKeyword2, invalidKeyword2;
    private String validDate2From, validDate2To, invalidDate2From, invalidDate2To;
            
    @Before
    public void setUp() throws Exception {
        tourCatalog = new TourCatalog();
        validKeyword1 = "Arctic Fox hunt";
        invalidKeyword1 = "Nonesense";
        validKeyword2 = "Horse-riding adventure";
        invalidKeyword2 = "Goat-riding adventure";
        tourCatalog = new TourCatalog();
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
    
    @Test // Should not be empty. Could check if it contains the correct Tour,
    public void testSearchByValidKeyword() { // but checking manually is less work
        assertFalse(tourCatalog.getToursBySearchParameters(validKeyword1,"","").isEmpty());
    }
    
    @Test // Should be empty
    public void testSearchByInvalidKeyword() {
        assertTrue(tourCatalog.getToursBySearchParameters(invalidKeyword1,"","").isEmpty());
    }
    
    @Test // Should never be empty
    public void testSearchByValidKeywordRepeatedly() {
        assertFalse(tourCatalog.getToursBySearchParameters(validKeyword1,"","").isEmpty() &&
                    tourCatalog.getToursBySearchParameters(validKeyword2,"","").isEmpty());
    }
    
    @Test // Should always be empty first
    public void testSearchByInvalidKeywordRepeatedly() {
        assertTrue(tourCatalog.getToursBySearchParameters(invalidKeyword1,"","").isEmpty() &&
                    tourCatalog.getToursBySearchParameters(invalidKeyword2,"","").isEmpty());
    }
    
    @Test // Should be empty, and then not empty. False overall
    public void testSearchByInvalidKeywordFollowedByValidKeyword() {
        assertFalse(tourCatalog.getToursBySearchParameters(invalidKeyword1,"","").isEmpty() &&
                    tourCatalog.getToursBySearchParameters(validKeyword1,"","").isEmpty());
    }
    
    @Test  // Should never be empty
    public void testSearchByValidDate() throws ParseException {
        assertFalse(tourCatalog.getToursBySearchParameters("",validDate2From, validDate2To).isEmpty());
    }    
    
    @Test // Should be empty if such scenario could occur (but BrowseTourController prevents "invalid" dates)
    public void testSearchByInvalidDateAndValidKeyword() throws ParseException {
        assertTrue(tourCatalog.getToursBySearchParameters(validKeyword1,invalidDate2From, invalidDate2To).isEmpty());
    }
    
    @Test // Should be empty
    public void testSearchByValidDateAndInalidKeyword() throws ParseException {
        assertTrue(tourCatalog.getToursBySearchParameters(invalidKeyword1,validDate2From, validDate2To).isEmpty());
    }
    
    @Test // Should not be empty
    public void testSearchByValidDateAndValidKeyword() throws ParseException {
        assertFalse(tourCatalog.getToursBySearchParameters(validKeyword1,invalidDate2From, validDate2To).isEmpty());
    }
    @Test // Should not be empty
    public void testSearchByWithAllParametersEmpty() throws ParseException {
        assertFalse(tourCatalog.getToursBySearchParameters("", "", "").isEmpty());
    }
    /*
    //Should not be empty, but may be empty for now or contain Tours that occur before the Date
    @Test  //-> Test may fail. More implementation necessary
    public void testSearchByValidFromDateButNoToDate() throws ParseException {
        assertFalse(tourCatalog.getToursBySearchParameters("",validDate2From, "").isEmpty());
    }
    
    //Should not be empty, but may be empty for now or contain Tours that occur after the specified Date
    @Test  //-> Test may fail. More implementation necessary
    public void testSearchByNoFromDateButValidToDate() throws ParseException {
        assertFalse(tourCatalog.getToursBySearchParameters("","", validDate2To).isEmpty());
    }
    */
}
