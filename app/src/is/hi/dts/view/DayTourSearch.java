package is.hi.dts.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Hlutverk klasans
 * @author  Andrea Osk Sigurdardottir aos26@hi.is
 *          David Freyr Bjornsson dfb2@hi.is
 *          Erling Oskar Kristjansson eok4@hi.is
 * @date
 * Háskóli Íslands
 */

// Microsoft Design Basics
// https://docs.microsoft.com/en-us/windows/uwp/design/basics/


public class DayTourSearch extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("DTSMain.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Day Tour Search");

        stage.setHeight(640);
        stage.setWidth(800);
    //    stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
