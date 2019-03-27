package en.hi.dtsapp.controller;

import en.hi.dtsapp.model.Tour;
import en.hi.dtsapp.model.TourDAO;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class objective
 * Accepts input, optionally validates it, and converts it to commands for the model or view.
 * 
 * 
 * @author Erling Óskar Kristjánsson eok4@hi.is
 * @date
 * Háskóli Íslands
 */
public class TourCatalog {
    
    private final List<Tour> TOUR_LIST;
    private final ObservableList<Tour> observableTourList;
    
    public TourCatalog() throws Exception {
        
        this.TOUR_LIST = TourDAO.initiateTourCatalog();
        observableTourList =  FXCollections.observableArrayList(TOUR_LIST);
    }
    
    public ObservableList<Tour> getObservableTourList() {
        return observableTourList;
    }

    
    
    // Basically a test method
    public void displaySomeTours() {
        System.out.println("Displaying some Tours:");
        System.out.println(observableTourList.get(10));
        System.out.println(observableTourList.get(100));
        System.out.println(observableTourList.get(400));
        System.out.println(observableTourList.get(700));
    }
}
