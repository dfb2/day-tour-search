package en.hi.dtsapp.view;

import en.hi.dtsapp.controller.TourCatalog;
import en.hi.dtsapp.model.Tour;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Erling Óskar Kristjánsson eok4@hi.is
 */
public class BrowseToursController implements Initializable {
    
    @FXML // @FXML  
    private ListView<Tour> tourListView;
    @FXML // @FXML  
    private TextField searchField;
    
    private TourCatalog tourCatalog;
    private ObservableList<Tour> tourList;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            tourCatalog = new TourCatalog();
        } catch (Exception ex) { // At least get an empty list view
            System.out.println("Failed to initialize Tour Catalog or ListView in BrowseToursController");
        }
        tourList = tourCatalog.getFullTourList();
        tourListView.setItems(tourList);
        tourCatalog.displaySomeTours(); // A test method

        /*        
            SortedList<Tour> sortedList = tourCatalog.getObservableTourList().sorted();

            FilteredList<String> filteredData = new FilteredList<>(data, s -> true);

            TextField filterInput = new TextField();
            filterInput.textProperty().addListener(obs->{
                String filter = filterInput.getText(); 
                if(filter == null || filter.length() == 0) {
                    filteredData.setPredicate(s -> true);
                }
                else {
                    filteredData.setPredicate(s -> s.contains(filter));
                }
            });
        */
    }
    
    @FXML
    private void browseDistinctTours() {
        tourListView.setItems(tourCatalog.getDistinctNameTourList());
    }
    
    @FXML
    private void browseToursByKeyword() {
        if (searchField == null || searchField.getText().isEmpty()){
            searchField.setText("Type keywords here");
        } else {
            String kw = searchField.getText(); 
            tourList = tourCatalog.getFullTourList();
            tourList = tourList.filtered(s -> s.getKeywords().contains(kw) || s.getName().contains(kw) ||
                    s.getInfo().contains(kw) || s.getLocation().contains(kw) || s.getOperator().contains(kw));
            tourListView.setItems(tourList);
        }
        
    }
    
    
    
}
