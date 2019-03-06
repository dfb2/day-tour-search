package contactbrowser;
import javafx.application.Application;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.collections.transformation.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.ListCell;
public class ContactBrowser extends Application {
    // List of contact table properties
   private String[] propertyName = {"id",
      "name", "nickName", "address",
      "homePhone", "workPhone", "cellPhone",
      "email", "birthDate", "webSite",
      "profession"};
   private String[] propertyLabel = {"ID",
      "Name", "Nick Name", "Address",
      "Home Phone", "Work Phone", "Cell Phone",
      "Email", "Birth Date", "Website",
      "Profession"};
   private ContactDAO contact = new ContactDAO();
   private final GridPane gridPane = new GridPane();
   private final Label lblName = new Label("Search by Name");
   private final TextField searchField = new TextField();
   private ObservableList<ContactPerson> observableNames;
   private FilteredList<ContactPerson> filteredData;
   private SortedList<ContactPerson> sortedData;
   private ListView<ContactPerson> listView;
   TableView<ContactPerson> contactTableView =
      new TableView<>();
   public ContactBrowser() {
      lblName.setTextFill(Color.web("#0076a3"));
      observableNames = FXCollections.observableArrayList
         (contact.getContacts());
      filteredData = new FilteredList<>
         (observableNames, p -> true);
      sortedData = new SortedList<>(filteredData);
      listView = new ListView<>(sortedData);
   }
   @Override
   public void start(Stage primaryStage) {
      primaryStage.setTitle("Address Book");
      primaryStage.setMaximized(true);
      BorderPane borderPane = new BorderPane();
      Scene scene = new Scene(borderPane,650,400,true);
      gridPane.setPadding(new Insets(10));
      gridPane.setHgap(5);
      gridPane.setVgap(5);
      gridPane.add(lblName, 0, 0);
      gridPane.add(searchField, 0, 1);
      // Search TextField event handling
      searchField.textProperty()
         .addListener((observable, oldValue, newValue) ->
            filteredData.setPredicate(str -> {
               if (newValue == null || newValue.isEmpty())
                  return true;
               if (str.getName().toLowerCase().contains
                     (newValue.toLowerCase()))
                  return true;
               return false;
      }));
      listView.getSelectionModel().setSelectionMode
         (SelectionMode.SINGLE);
      listView.setPrefHeight(Integer.MAX_VALUE);
      // Sets a new cell factory to use in the ListView.
      // This throws away all old list cells and new ListCells
      // created with the new cell factory.
      listView.setCellFactory(listView-> {
         Tooltip tooltip = new Tooltip();
         ListCell<ContactPerson> cell = new
               ListCell<ContactPerson>() { 
            public void updateItem(ContactPerson contactPerson,
                  Boolean empty) {
               super.updateItem(contactPerson, empty);
               if (contactPerson != null) {
                  setText(contactPerson.getName());
                  tooltip.setText(contactPerson.getNickName());
                  setTooltip(tooltip);
               } else
                  setText(null);
            }
         };
         return cell;
      });
      gridPane.add(listView, 0, 2);
      // Create and initializing TableView
      ObservableList<ContactPerson> contactPeopleList
         = FXCollections.observableArrayList();
      contactTableView.setItems(contactPeopleList);
      contactTableView.setColumnResizePolicy(
         TableView.CONSTRAINED_RESIZE_POLICY);
      for (int i = 0; i < propertyLabel.length; i++) {
         TableColumn<ContactPerson, Object> col
            = new TableColumn<>(propertyLabel[i]);
         col.setCellValueFactory(new
            PropertyValueFactory<>(propertyName[i]));
         contactTableView.getColumns().add(col);
      }
      borderPane.setCenter(contactTableView);
      borderPane.setLeft(gridPane);
      // TableView will populate from the contactPeopleList
      // contactPeopleList will have value according to the
      // item selected in the ListView
      listView.getSelectionModel()
         .selectedItemProperty()
         .addListener(new ChangeListener<ContactPerson>() {
            @Override
            public void changed(
               ObservableValue<? extends
                  ContactPerson> observable,
               ContactPerson oldValue, ContactPerson newValue) {
               if (observable != null &&
                     observable.getValue() != null) {
                  contactPeopleList.clear();
                  contactPeopleList.addAll(
                     contact.getContactsForName
                        (newValue.getName()));
                  }
               }
            });
      primaryStage.setScene(scene);
      primaryStage.show();
   }
   public static void main(String[] args) {
      launch (args);
   }
}