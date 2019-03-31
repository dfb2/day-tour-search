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
    private final List<Tour> DISTINCT_NAME_TOUR_LIST;
//    private final ObservableList<Tour> observableTourList;
    
    public TourCatalog() throws Exception {
        this.TOUR_LIST = TourDAO.initiateTourCatalog();
        this.DISTINCT_NAME_TOUR_LIST = TourDAO.distinctTourCatalog();
 //       observableTourList =  FXCollections.observableArrayList(TOUR_LIST);
    }
    
    public ObservableList<Tour> getFullTourList() {
        return FXCollections.observableArrayList(TOUR_LIST);
    }
    
    public ObservableList<Tour> getDistinctNameTourList() {
        return FXCollections.observableArrayList(DISTINCT_NAME_TOUR_LIST);
    }
    
    public ObservableList<Tour> getToursByKeyword(String kw) {
            ObservableList <Tour> fullTourList = this.getFullTourList();
            ObservableList <Tour> newTourList = fullTourList.filtered(s -> s.getKeywords().contains(kw) 
                    || s.getName().contains(kw) 
                    || s.getInfo().contains(kw) 
                    || s.getLocation().contains(kw) 
                    || s.getOperator().contains(kw));
            return newTourList;
    }
    
    /*
    public ObservableList<Tour> getObservableTourListOrderByPrice() {
        switch priceOrder:
                case 0:
                    priceOrder=1;
                    return observableTourList.sorted(comparator);
                case 1:
                    priceOrder=-1;
                    return observableTourList.;
        return observableTourList.sorted(comparator);
    }
    */
   
    // Basically a test method
    public void displaySomeTours() {
        System.out.println("Displaying some Tours:");
        System.out.println(TOUR_LIST.get(10));
        System.out.println(TOUR_LIST.get(100));
        System.out.println(TOUR_LIST.get(400));
        System.out.println(TOUR_LIST.get(700));
    }
}
