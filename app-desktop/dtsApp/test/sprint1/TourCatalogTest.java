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
    
    
    public TourCatalogTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        tourCatalog = new TourCatalog();
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
   //     assert(tourCatalog.getFullTourList();
    }
    
    @Test
    public void testSearchByInvalidKeyword() {
    //    assert(tourCatalog.getFullTourList();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
