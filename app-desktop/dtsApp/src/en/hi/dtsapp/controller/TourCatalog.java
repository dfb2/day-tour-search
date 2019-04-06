package en.hi.dtsapp.controller;

import en.hi.dtsapp.model.Tour;
import en.hi.dtsapp.model.TourDAO;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*****************************************************************************************************************************
 * Class objective: 
 * Accepts input, optionally validates it, and converts it to commands for the model or view.
 *****************************************************************************************************************************
 * Here's what you need to know to use it:
 *      public TourCatalog() 
 *          constructor. Builds two private variables:
 *          TOUR_LIST contains every single Tour from the Tour database
 *          DISTINCT_NAME_TOUR_LIST contains only one of each (by name, i.e. no repetitions of the same tour on other dates)
 * 
 *       public ObservableList<Tour> getFullTourList() 
 *          returns an ObserveableList copy of TOUR_LIST . Every single tour from the database
 * 
 *      public ObservableList<Tour> getDistinctNameTourList()
 *          returns an ObserveableList copy of DISTINCT_NAME_TOUR_LIST (for easy viewing)
 * 
 ****************************************************************************************************************************
 *      HERE'S THE ONE AND ONLY FUNCTION YOU NEED TO BE USING:
 *      public ObservableList<Tour> getToursBySearchParameters(String keyword, String from, String to)
 *          returns an ObserveableList copy of TOUR_LIST,
 *              filtered by         keyword (String that can be Tour location, operator, name, keyword or info
 *                                       from (String that represents a date in the format ddmmyyyy)
 *                        and         to (String that represents a date in the format ddmmyyyy)
 *              If these Strings are empty or null, that part of the filtering method will not filter it.
 *          More filter parameters may be added later, in which case you will be notified.
 ****************************************************************************************************************************
 * @author Erling Óskar Kristjánsson eok4@hi.is
 * Háskóli Íslands
 */
public class TourCatalog {
    
    private final List<Tour> TOUR_LIST;
    private final List<Tour> DISTINCT_NAME_TOUR_LIST;
    
    public TourCatalog() throws Exception {
        this.TOUR_LIST = TourDAO.initiateTourCatalog();
        this.DISTINCT_NAME_TOUR_LIST = TourDAO.distinctTourCatalog();
        displaySomeTours();
    }
    
    public ObservableList<Tour> getFullTourList() {
        return FXCollections.observableArrayList(TOUR_LIST);
    }
    
    public ObservableList<Tour> getDistinctNameTourList() {
        return FXCollections.observableArrayList(DISTINCT_NAME_TOUR_LIST);
    }
    
    // If any parameter is "" or null, that part of the filtering of this.getFullTourList() will be ignored
    public ObservableList<Tour> getToursBySearchParameters(
            String keyword, LocalDate dateFrom, LocalDate dateTo) {
        ObservableList <Tour> newTourList = this.getFullTourList();
        if(!(keyword == null || keyword.isEmpty() 
                || keyword.contains("Search") || "Type keywords here or pick dates".equals(keyword))){
            newTourList = getToursByKeyword(newTourList, keyword.toLowerCase());
        }
        if(dateFrom == null) dateFrom = LocalDate.now();
        if(dateTo == null) dateTo = LocalDate.MAX;
        newTourList = getToursByDate(newTourList, dateFrom, dateTo);
        return newTourList;
    }
    
    private ObservableList<Tour> getToursByKeyword(ObservableList<Tour> fullTourList, String keyword) {
            ObservableList <Tour> newTourList = fullTourList.filtered(
                       s -> s.getKeywords().toLowerCase().contains(keyword) 
                    || s.getName().toLowerCase().contains(keyword) 
                    || s.getInfo().toLowerCase().contains(keyword) 
                    || s.getLocation().toLowerCase().contains(keyword) 
                    || s.getOperator().toLowerCase().contains(keyword) );
            return newTourList;
    }

    // returns an empty list if dateFrom is after dateTo
     private ObservableList<Tour> getToursByDate(
             ObservableList<Tour> fullTourList, LocalDate dateFrom, LocalDate dateTo) {
         if(dateFrom == null) dateFrom = LocalDate.now(); // 3 unnecesary lines because this is a private function??
         if(dateTo == null) dateTo = LocalDate.MAX;
         System.out.println(dateFrom.toString() + "\n"+ dateTo.toString());
         ObservableList  <Tour> newTourList = FXCollections.observableArrayList();
         for(Tour s: fullTourList) {
            LocalDate sDate = s.getDate();
            if((sDate.equals(dateFrom) || sDate.isAfter(dateFrom))
                && (sDate.equals(dateTo) || sDate.isBefore(dateTo))) newTourList.add(s);
         }
         return newTourList;
    }
   
    private void displaySomeTours() {     // Basically a test method
        System.out.println("Displaying some Tours:");
        System.out.println(TOUR_LIST.get(10).getStartTime());
        System.out.println(TOUR_LIST.get(100).getStartTime());
        System.out.println(TOUR_LIST.get(700).getStartTime());
    }
}
