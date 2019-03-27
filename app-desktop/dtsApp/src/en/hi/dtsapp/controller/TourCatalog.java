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
//    private ObservableList<Tour> observableTourList;
    
    public TourCatalog() throws Exception {
        
        this.TOUR_LIST = TourDAO.initiateTourCatalog();
//        observableTourList =  FXCollections.ObservableArrayList();
    }
}
