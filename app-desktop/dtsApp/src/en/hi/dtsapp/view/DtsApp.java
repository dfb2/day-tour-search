package en.hi.dtsapp.view;

import en.hi.dtsapp.model.TourDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Hlutverk klasans
 * @author Erling Óskar Kristjánsson eok4@hi.is
 * @date
 * Háskóli Íslands
 */
public class DtsApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("BrowseTours.fxml"));

        Scene scene = new Scene(root, 1100, 500);

        primaryStage.setTitle("Day Tour Search!");
        primaryStage.setScene(scene);
        
        // Determined minimum size from https://dzone.com/articles/statically-compiled-groovy-or-time-to-give-groovy 
        primaryStage.setMinHeight(250);
        primaryStage.setMinWidth(500);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
        // It's possible to call the test method for TourDAO.java
        // TourDAO.main(); 
    }

}
