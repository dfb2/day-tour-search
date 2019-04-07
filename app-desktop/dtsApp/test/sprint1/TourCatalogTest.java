package sprint1;

import en.hi.dtsapp.controller.TourCatalog;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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
    private LocalDate validDate2From, validDate2To, invalidDate2From, invalidDate2To;
    private final List<String> keywordExceptions = Arrays.asList("test1", "test2", "test3");
            
    @Before
    public void setUp() throws Exception {
        tourCatalog = new TourCatalog();
        validKeyword1 = "Arctic Fox hunt";
        invalidKeyword1 = "Nonesense";
        validKeyword2 = "Horse-riding adventure";
        invalidKeyword2 = "Goat-riding adventure";
        tourCatalog = new TourCatalog();
        validDate2From = LocalDate.of(2019, 5, 5);          
        validDate2To = LocalDate.of(2019, 5, 7);
        invalidDate2From = LocalDate.of(11290, 4, 1);
        invalidDate2To = LocalDate.of(11290, 4, 5);
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
        assertFalse(tourCatalog.getToursBySearchParameters(validKeyword1, 
                null, null, keywordExceptions).isEmpty());
    }
    
    @Test // Should be empty
    public void testSearchByInvalidKeyword() {
        assertTrue(tourCatalog.getToursBySearchParameters(invalidKeyword1,
                null, null, keywordExceptions).isEmpty());
    }
    
    @Test // Should never be empty
    public void testSearchByValidKeywordRepeatedly() {
        assertFalse(tourCatalog.getToursBySearchParameters(validKeyword1,
                null, null, keywordExceptions).isEmpty() &&
                    tourCatalog.getToursBySearchParameters(validKeyword2,
                null, null, keywordExceptions).isEmpty());
    }
    
    @Test // Should always be empty first
    public void testSearchByInvalidKeywordRepeatedly() {
        assertTrue(tourCatalog.getToursBySearchParameters(invalidKeyword1,
                null, null, keywordExceptions).isEmpty() &&
                   tourCatalog.getToursBySearchParameters(invalidKeyword2,
                null, null, keywordExceptions).isEmpty());
    }
    
    @Test // Should be empty, and then not empty. False overall
    public void testSearchByInvalidKeywordFollowedByValidKeyword() {
        assertFalse(tourCatalog.getToursBySearchParameters(invalidKeyword1,
                null, null, keywordExceptions).isEmpty() &&
                    tourCatalog.getToursBySearchParameters(validKeyword1,
                null, null, keywordExceptions).isEmpty());
    }
    
    @Test  // Should never be empty
    public void testSearchByValidDate() throws ParseException {
        assertFalse(tourCatalog.getToursBySearchParameters("",validDate2From,
                validDate2To, keywordExceptions).isEmpty());
    }    
    
    @Test // Should be empty if such scenario could occur (but BrowseTourController prevents "invalid" dates)
    public void testSearchByInvalidDateAndValidKeyword() throws ParseException {
        assertTrue(tourCatalog.getToursBySearchParameters(validKeyword1,
                invalidDate2From, invalidDate2To, keywordExceptions).isEmpty());
    }
    
    @Test // Should be empty
    public void testSearchByValidDateAndInalidKeyword() throws ParseException {
        assertTrue(tourCatalog.getToursBySearchParameters(invalidKeyword1,
                validDate2From, validDate2To, keywordExceptions).isEmpty());
    }
    
    @Test // Should not be empty
    public void testSearchByValidDateAndValidKeyword() throws ParseException {
        assertFalse(tourCatalog.getToursBySearchParameters(validKeyword1,
                validDate2From, validDate2To, keywordExceptions).isEmpty());
    }
    @Test // Should not be empty
    public void testSearchByWithAllParametersEmpty() throws ParseException {
        assertFalse(tourCatalog.getToursBySearchParameters("", null, 
                null, keywordExceptions).isEmpty());
    }
    @Test
    public void testSearchByKeywordException() throws ParseException {
        assertEquals(tourCatalog.getToursBySearchParameters(
                keywordExceptions.get(0), null, null, keywordExceptions),
                tourCatalog.getToursBySearchParameters(
                "", null, null, keywordExceptions));
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
