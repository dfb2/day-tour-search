package en.hi.dtsapp.view;

import en.hi.dtsapp.controller.TourCatalog;
import en.hi.dtsapp.model.Tour;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Erling Óskar Kristjánsson eok4@hi.is
 */
public class BrowseToursController implements Initializable {
    
    @FXML // @FXML  
    private ListView<Tour> tourListView;
    
    
    TourCatalog tourCatalog;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            tourCatalog = new TourCatalog();
            tourListView.setItems(tourCatalog.getObservableTourList());
        } catch (Exception ex) { // At least get an empty list view
            System.out.println("Failed to initialize Tour Catalog or ListView in BrowseToursController");
        }
        tourCatalog.displaySomeTours(); // A test method
    }
    
}
