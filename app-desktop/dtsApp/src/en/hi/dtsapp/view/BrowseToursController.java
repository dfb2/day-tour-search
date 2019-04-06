package en.hi.dtsapp.view;

import en.hi.dtsapp.controller.TourCatalog;
import en.hi.dtsapp.model.Tour;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 * Receives input from user. Validates most of it. Sends it to appropriate controllers.
 *      Keep the function clearSearchParameters() at the bottom.
 *      If you add parameters that may need to be cleared, make sure to add them there.
 * @author Erling Óskar Kristjánsson eok4@hi.is
 */
public class BrowseToursController implements Initializable {
    
    @FXML
    private ListView<Tour> tourListView;
    @FXML
    private TextField searchField;
    
    @FXML
    private DatePicker dateFromField, dateToField;
    private LocalDate dateFrom, dateTo;
    
    private TourCatalog tourCatalog;
    private ObservableList<Tour> tourList;
    @FXML
    private MenuItem passengerMenuItem1, passengerMenuItem2, 
            passengerMenuItem3, passengerMenuItem4,
            passengerMenuItem5, passengerMenuItem6, 
            passengerMenuItem7, passengerMenuItem8;
    private MenuItem[] PassengerMenuItems;
    private int selectedPassengers;
    @FXML
    private SplitMenuButton PassengerSplitMenu;
    
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
        PassengerMenuItems = new MenuItem[]{
            passengerMenuItem1, passengerMenuItem2, passengerMenuItem3, passengerMenuItem4,
            passengerMenuItem5, passengerMenuItem6, passengerMenuItem7, passengerMenuItem8
        };
        selectedPassengers = 1;
    } 
    @FXML
    private void browseDistinctTours() {
        tourListView.setItems(tourCatalog.getDistinctNameTourList());
    }
    
    @FXML // Passar upp á NullPointerExceptions og tóm input
    private void searchForTours() {
        // DTSMethods.DATE_FORMATTER();
        if(dateFromField.getValue() == null) dateFrom = LocalDate.now(); 
        else dateFrom = dateFromField.getValue();
        if(dateToField.getValue() == null) dateTo = LocalDate.MAX;
         else dateTo = dateToField.getValue();
        if ( (searchField.getText().isEmpty() || searchField.getText().contains("Search"))
                && dateFrom == LocalDate.now() && dateTo == LocalDate.MAX) {
            searchField.setPromptText("Search...");
            // tourList = tourCatalog.getFullTourList();
            tourListView.setItems(tourList);
        } else {
            ObservableList<Tour> filteredList = 
                    tourCatalog.getToursBySearchParameters(searchField.getText(), dateFrom, dateTo);
            tourListView.setItems(filteredList);
        }
    }

    @FXML // Reacts to number of passengers being selected from drop-down menu
    private void storeCurrentPassengers(ActionEvent event) {
        MenuItem menuItem = (MenuItem)event.getSource(); 
        setCurrentPassengers(numberOfPassengers(menuItem));
    }
    
    // sets the currently selected numbers of passengers and displays as the drop-down menu text
    private void setCurrentPassengers(int currPass){
        selectedPassengers = currPass;
        String s = String.valueOf(selectedPassengers).concat(" Passenger");
        if(selectedPassengers>1) s = s.concat("s");
        PassengerSplitMenu.setText(s);
    }

    // returns the index of menuItem (number of passengers that was selected)
    private int numberOfPassengers(MenuItem menuItem) {
        for(int i = 0; i < PassengerMenuItems.length; i++) {
            if(menuItem == PassengerMenuItems[i]) return i+1;
        }
        return 11;
    }

    @FXML
    private void clearSearchParameters() {
        searchField.setPromptText("Search...");
        try{ dateTo=null; dateToField.setValue(null); } catch(NullPointerException e) { dateTo=null; dateToField.setValue(null);}
        try{ dateFrom=null; dateFromField.setValue(null); } catch(NullPointerException e) { dateFrom=null; dateFromField.setValue(null);}
        setCurrentPassengers(1);
        searchForTours();
    }

    @FXML
    private void bookSelectedTour(ActionEvent event) {
        // Update tourListView to show all tours
    //    tourListView.setItems(tourList);
        
        System.out.println("Booked a tour");
        System.out.print("Name: ");
        System.out.print(tourListView.getSelectionModel().getSelectedItem().
                getName());
        System.out.print("\n Number of passengers before booking: ");
        System.out.println(tourListView.getSelectionModel().
                getSelectedItem().getTravelers());
        
        // Update the number of passengers in the ListView
        tourListView.getSelectionModel().getSelectedItem().addTravelers(
                selectedPassengers);
        // Print the current number of passenger for the
        // selected Tour
          System.out.print("Number of passengers after booking: ");
        System.out.println(tourListView.getSelectionModel().
                getSelectedItem().getTravelers());
        
        // Update the tourList with the number of passengers in the ListView
        tourList = tourListView.getItems();

    }
}