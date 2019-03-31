package sprint1;

import en.hi.dtsapp.controller.TourCatalog;
import en.hi.dtsapp.model.Tour;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Erling Óskar Kristjánsson eok4@hi.is
 */
public class TourCatalogTest {
    
    private TourCatalog tourCatalog;
    private String validKeyword1, invalidKeyword1;
    private String validKeyword2, invalidKeyword2;
    
    @Before
    public void setUp() throws Exception {
        tourCatalog = new TourCatalog();
        validKeyword1 = "Arctic Fox hunt";
        invalidKeyword1 = "Nonesense";
        validKeyword2 = "Horse-riding adventure";
        invalidKeyword2 = "Goat-riding adventure";
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
    public void testSearchByValidKeyword() {
        assertFalse(tourCatalog.getToursByKeyword(validKeyword1).isEmpty());
    }
    
    @Test
    public void testSearchByInvalidKeyword() {
        assertTrue(tourCatalog.getToursByKeyword(invalidKeyword1).isEmpty());
    }
    
    @Test
    public void testSearchByValidKeywordRepeatedly() {
        assertFalse(tourCatalog.getToursByKeyword(validKeyword1).isEmpty() &&
                    tourCatalog.getToursByKeyword(validKeyword2).isEmpty());
    }
    
    @Test
    public void testSearchByInvalidKeywordRepeatedly() {
        assertTrue(tourCatalog.getToursByKeyword(invalidKeyword1).isEmpty() &&
                    tourCatalog.getToursByKeyword(invalidKeyword2).isEmpty());
    }
}
