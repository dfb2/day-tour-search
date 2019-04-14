package en.hi.dtsapp.view;

import en.hi.dtsapp.controller.TourCatalog;
import en.hi.dtsapp.model.tours.Tour;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
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
import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;

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
    private String cpName = "DummyCustomer";
    private String cpPassword = "dummyPassword";
    private String cpEmail = "dummyCustomer@hi.is";
    
    @FXML private ListView<Tour> tourListView;
    private TourCatalog tourCatalog;
    private ObservableList<Tour> tourList;
    @FXML private TextField searchField;
    
    @FXML private DatePicker dateFromField, dateToField;
    private LocalDate dateFrom, dateTo;
    
    @FXML private MenuItem passengerMenuItem1, passengerMenuItem2, 
            passengerMenuItem3, passengerMenuItem4,
            passengerMenuItem5, passengerMenuItem6, 
            passengerMenuItem7, passengerMenuItem8;
    private MenuItem[] PassengerMenuItems;
    private int selectedPassengers;
    @FXML private SplitMenuButton PassengerSplitMenu;
    
    
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
        } catch (ClassNotFoundException | SQLException | ParseException ex) {
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
             tourCatalog.setCustomerPerson(cpName, cpPassword, cpEmail);
            // customerPersonCatalog = CustomerDAO.initiateCustomerCatalog();
        } catch (ClassNotFoundException | IllegalArgumentException | SQLException ex) {
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


    /**
     * 
     * @param event User clicked the Book Selected Tour button
     * @throws IllegalArgumentException 
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @FXML
    private void bookSelectedTour(ActionEvent event) {
        if(tourListView.getSelectionModel().getSelectedItem() == null) return;
        // Update the actual tour in the tour catalog
        Tour tour = tourListView.getSelectionModel().getSelectedItem();
        try{
            boolean result = tourCatalog.bookTour(tour, selectedPassengers);        
        }
        catch(IllegalArgumentException iae) {
            System.err.println("Caught IllegalArgException in BrowseToursController.bookSelectedTour()");
            System.err.println(iae.getMessage());
        }
        catch(ClassNotFoundException cnfe) {
            System.err.println("Caught ClassNotFoundExc in BrowseToursController.bookSelectedTour()");
            System.err.println(cnfe.getMessage());
        }
        catch(SQLException ex) {
            System.err.println("Caught SQLExc in BrowseToursController.bookSelectedTour()");
            System.err.println(ex.getMessage());
        }
        
        // And refresh the information in the listView
        tourListView.refresh();
    }

    @FXML // Creates a dialog in response to user clicking Login/SignUp button 
    private void createCustomerPersonDialog(ActionEvent event) {
        Dialog dialog = new Dialog();
        dialog.setTitle("Login/SignUp");
        dialog.setHeaderText("Please enter your details:");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the input labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField name = new TextField();
        name.setPromptText("Name");
        TextField email = new TextField();
        email.setPromptText("Email");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        
        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(email, 1, 1);
        grid.add(new Label("Password:"), 0, 2);
        grid.add(password, 1, 2);
        
        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> name.requestFocus());

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            cpName = name.getText();
            cpPassword = password.getText();
            cpEmail = email.getText();
            try {
                  tourCatalog.setCustomerPerson(cpName, cpPassword, cpEmail);
             } catch (ClassNotFoundException | IllegalArgumentException | SQLException ex) {
                 System.err.println(ex.getMessage());
             }
        }
    }
}