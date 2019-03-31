package en.hi.dtsapp.view;

import en.hi.dtsapp.controller.TourCatalog;
import en.hi.dtsapp.model.Tour;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 * Receives input from user. Validates most of it. Sends it to appropriate controllers.
 *      Keep the function clearSearchParameters() at the bottom.
 *      If you add parameters that may need to be cleared, make sure to add them there.
 * @author Erling Óskar Kristjánsson eok4@hi.is
 */
public class BrowseToursController implements Initializable {
    
    @FXML // @FXML  
    private ListView<Tour> tourListView;
    @FXML // @FXML  
    private TextField searchField;
    
    @FXML
    private DatePicker dateFromField, dateToField;
    private String dateFrom, dateTo;
    
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
        } catch (Exception ex) {
            System.out.println("Failed to initialize Tour Catalog or ListView in BrowseToursController");
        }
        tourList = tourCatalog.getFullTourList();
        tourListView.setItems(tourList);
    }
    
    @FXML
    private void browseDistinctTours() {
        tourListView.setItems(tourCatalog.getDistinctNameTourList());
    }
    
    @FXML // Passar upp á NullPointerExceptions og tóm input
    private void searchForTours() { 
        try{ if(dateTo == null) dateTo=""; } catch(NullPointerException e) { dateTo=""; }
        try{ if(dateFrom == null) dateFrom=""; } catch(NullPointerException e) { dateFrom=""; }
        if ( (searchField.getText().isEmpty() || searchField.getText().contains("Search"))
                && dateFrom.isEmpty() && dateTo.isEmpty()){
            searchField.setPromptText("Search...");
            tourList = tourCatalog.getFullTourList();
            tourListView.setItems(tourList);
        } else {
            ObservableList<Tour> filteredList = 
                    tourCatalog.getToursBySearchParameters(searchField.getText(), dateFrom, dateTo);
            tourListView.setItems(filteredList);
        }
    }
    
    @FXML
    private void storeSelectedFromDate() {
        try{ if(dateFromField.getValue() == null) return; } catch (NullPointerException e){ return; }
        String pattern = "ddMMyyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
        dateFrom = dateFromField.getValue().format(dateFormatter);
        System.out.println("Chosen from date is " + dateFrom);
    }
    
    @FXML
    private void storeSelectedToDate() {
        try{ if(dateToField.getValue() == null) return; } catch (NullPointerException e){ return; }
        String pattern = "ddMMyyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
        dateTo = dateToField.getValue().format(dateFormatter);
        System.out.println("Chosen to date is " + dateTo);    
    }

    @FXML
    private void clearSearchParameters() {
        searchField.setPromptText("Search...");
        try{ dateTo=""; dateToField.setValue(null); } catch(NullPointerException e) { dateTo=""; dateToField.setValue(null);}
        try{ dateFrom=""; dateFromField.setValue(null); } catch(NullPointerException e) { dateFrom=""; dateFromField.setValue(null);}
        searchForTours();
    }
    


}
