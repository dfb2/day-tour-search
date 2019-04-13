package en.hi.dtsapp.view;

import en.hi.dtsapp.model.people.CustomerPerson;
import en.hi.dtsapp.controller.TourCatalog;
import en.hi.dtsapp.model.tours.Tour;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
import java.util.List;


/**
 * FXML Controller class
 * Receives input from user. Validates most of it. Sends it to appropriate controllers.
 *      Keep the function clearSearchParameters() at the bottom.
 *      If you add parameters that may need to be cleared, make sure to add them there.
 * @author Erling Óskar Kristjánsson eok4@hi.is
 */
public class BrowseToursController implements Initializable {
    
    String[] kwEx = {"Search", "Type keywords here or pick dates", "Search..."};
    private List<String> KeywordExceptions;
    
 //   private List<CustomerPerson> customerPersonCatalog;
    private CustomerPerson customerPerson;
    private final String cpName = "DummyCustomer";
    private final String cpPassword = "dummyPassword";
    private final String cpEmail = "dummyCustomer@hi.is";
    
    @FXML
    private ListView<Tour> tourListView;
    private TourCatalog tourCatalog;
    private ObservableList<Tour> tourList;
    @FXML
    private TextField searchField;
    
    @FXML
    private DatePicker dateFromField, dateToField;
    private LocalDate dateFrom, dateTo;
    
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
     * TourCatalog should be a class with methods to retrieve lists of Tour objects
     * Sets the ListView with items from TourCatalog.getFullTourList()
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            tourCatalog = new TourCatalog();
        } catch (Exception ex) {
            System.err.println("Failed to initialize Tour Catalog or ListView in BrowseToursController");
        }
        tourList = tourCatalog.getFullTourList();
        tourListView.setItems(tourList);
        PassengerMenuItems = new MenuItem[]{
            passengerMenuItem1, passengerMenuItem2, passengerMenuItem3, passengerMenuItem4,
            passengerMenuItem5, passengerMenuItem6, passengerMenuItem7, passengerMenuItem8
        };
        setCurrentPassengers(1);
        KeywordExceptions = new ArrayList<>(Arrays.asList(kwEx));
        
        try {
             customerPerson = new CustomerPerson(cpName, cpPassword, cpEmail);
            // customerPersonCatalog = CustomerDAO.initiateCustomerCatalog();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
          //  System.err.println("Failed to initialize customerPersonCatalog in BrowseToursController");
        }
        
    } 
    
    // Reacts to Browse Distinct button being pressed by User
    @FXML
    private void browseDistinctTours() {
        tourListView.setItems(tourCatalog.getDistinctNameTourList());
        tourListView.getSelectionModel().selectFirst(); 
    }
    
    // React to SearchForTours button being pressed by User
    // Takes care of some NullPointerExceptions and empty inputs
    @FXML
    private void searchForTours() {
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
                    tourCatalog.getToursBySearchParameters(searchField.getText(), dateFrom, dateTo, KeywordExceptions);
            tourListView.setItems(filteredList);
        }
        tourListView.getSelectionModel().selectFirst(); 
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
        searchField.setText("");
        searchField.setPromptText("Search...");
        try{ dateTo=null; dateToField.setValue(null); } catch(NullPointerException e) { dateTo=null; dateToField.setValue(null);}
        try{ dateFrom=null; dateFromField.setValue(null); } catch(NullPointerException e) { dateFrom=null; dateFromField.setValue(null);}
        setCurrentPassengers(1);
        searchForTours();
    }


    @FXML // Responds to the event of a user clicking the Book Selected Tour button
    private void bookSelectedTour(ActionEvent event) {
        if(tourListView.getSelectionModel().getSelectedItem() == null) return;
        // Update the actual tour in the tour catalog
        Tour tour = tourListView.getSelectionModel().getSelectedItem();
        int result = tourCatalog.bookTour(customerPerson, tour, selectedPassengers);
        // And refresh the information in the listView
        tourListView.refresh();
    }
}